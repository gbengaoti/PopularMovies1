package com.example.popularmoviespart1.model;

import androidx.annotation.NonNull;

public class Movie {
    private String title;
    private String posterPath;
    private String synopsis;
    private String userRating;
    private String releaseDate;

    public Movie(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NonNull
    @Override
    public String toString() {
        return (this.title + '\n'+ this.posterPath + '\n' + this.synopsis + '\n'+ this.releaseDate );
    }
}
