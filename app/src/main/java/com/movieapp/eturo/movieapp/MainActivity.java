package com.movieapp.eturo.movieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView[] images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img1 =(ImageView)findViewById(R.id.imageView1);
        ImageView img2= (ImageView)findViewById(R.id.imageView2);
        ImageView img3= (ImageView)findViewById(R.id.imageView3);
        ImageView img4= (ImageView)findViewById(R.id.imageView4);
        ImageView img5= (ImageView)findViewById(R.id.imageView5);
        ImageView img6= (ImageView)findViewById(R.id.imageView6);
        Picasso.with(this).load("http://4.bp.blogspot.com/-OI4R07zYTuI/T3hnA7V5g0I/AAAAAAAAAPU/i-G44RixBfk/s1600/wallpaper+barcelona-1.png").into(img1);
        Picasso.with(this).load("http://4.bp.blogspot.com/-OI4R07zYTuI/T3hnA7V5g0I/AAAAAAAAAPU/i-G44RixBfk/s1600/wallpaper+barcelona-1.png").into(img3);
        Picasso.with(this).load("http://4.bp.blogspot.com/-OI4R07zYTuI/T3hnA7V5g0I/AAAAAAAAAPU/i-G44RixBfk/s1600/wallpaper+barcelona-1.png").into(img2);
        Picasso.with(this).load("http://4.bp.blogspot.com/-OI4R07zYTuI/T3hnA7V5g0I/AAAAAAAAAPU/i-G44RixBfk/s1600/wallpaper+barcelona-1.png").into(img4);
        Picasso.with(this).load("http://4.bp.blogspot.com/-OI4R07zYTuI/T3hnA7V5g0I/AAAAAAAAAPU/i-G44RixBfk/s1600/wallpaper+barcelona-1.png").into(img5);
        Picasso.with(this).load("http://4.bp.blogspot.com/-OI4R07zYTuI/T3hnA7V5g0I/AAAAAAAAAPU/i-G44RixBfk/s1600/wallpaper+barcelona-1.png").into(img6);
    FetchMovieTask fetch = new FetchMovieTask();
        fetch.execute("hf");
    }

    public class FetchMovieTask extends AsyncTask<String,Void,Movie[]> {

        @Override
        protected void onPostExecute(Movie[] movies) {
            super.onPostExecute(movies);
                for(Movie m : movies)
                    Log.v("onPost", m.toString());
        }

        @Override
        protected Movie[] doInBackground(String... params) {

            Log.d("DoinBakcground", "In here");
            final String API_KEY = "ecee4a10af98838beec6ee3bfac6c8d5";
            final String API_Key_LABEL = "api_key";
            final String BASE_URL = "http://api.themoviedb.org/3/movie/popular";
            Movie[] movies=null;
            String movieJsonStr = null;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {

                Uri movie = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(API_Key_LABEL, API_KEY)
                        .build();
                URL url = new URL(movie.toString());
                //create connection to url to get data for a movie
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                //create stream to get json string
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null;
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null)
                    buffer.append(line + "\n");

                if (buffer.length() == 0)
                    return null;

                movieJsonStr = buffer.toString();
                try {
                    movies = getMovieDataFromJson(movieJsonStr);
                }
                catch(JSONException e){
                    Log.e("MainActivity","Error get JSON Data from movies",e);
                }
                return movies;

            } catch (IOException e) {
                Log.e("Movie DoInBackGround", "Error", e);
                return null;
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Movie", "Error closing stream", e);
                    }
                }
            }

        }

        private Movie[] getMovieDataFromJson(String movieJsonStr) throws JSONException{
            final String TMD_LIST="results";
            final String TMD_TITLE= "original_title";
            final String TMD_POSTER_PATH="poster_path";
            final String TMD_PLOT_SYNOPSIS="overview";
            final String TMD_USER_RATING="vote_average";
            final String TMD_RELEASE_DATE="release_date";
            final String TMD_ID="id";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray moviesArray = movieJson.getJSONArray(TMD_LIST);

            Movie[] movies = new Movie[6];

            for(int i =0; i< 6;i++){

                JSONObject currMovie = moviesArray.getJSONObject(i);
                movies[i] = new Movie(currMovie.getInt(TMD_ID),
                        currMovie.getString(TMD_PLOT_SYNOPSIS),
                        currMovie.getString(TMD_TITLE),
                        currMovie.getString(TMD_RELEASE_DATE),
                        currMovie.getDouble(TMD_USER_RATING),
                        currMovie.getString(TMD_POSTER_PATH));
            }

            return movies;
        }

    }




}
