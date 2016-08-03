package com.movieapp.eturo.movieapp;

/**
 * Created by Emilio on 2016-08-03.
 */
public class Movie {
    private String synopsis;
    private String title;
    private String releaseDate;
    private double userRating;
    private String posterPath;
    private int id;

    public Movie(int id,String synopsis, String title, String releaseDate, double userRating, String posterPath){
        this.synopsis = synopsis;
        this.title = title;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.posterPath = posterPath;
        this.id = id;
    }

    public String getSynopsis(){
        return synopsis;

    }

    public String getPosterPath(){
        return posterPath;
    }

    public String toString(){
        return title + "-" + synopsis + " " + releaseDate + " " + userRating;
    }

    public String getTitle(){
        return title;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public double getUserRating(){
        return userRating;
    }
}
