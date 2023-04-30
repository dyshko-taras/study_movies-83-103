package com.dyshkotaras.movies;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesVideosList {

    @SerializedName("results")
    private final List<MovieVideos> movieVideosList;

    public MoviesVideosList(List<MovieVideos> movieVideosList) {
        this.movieVideosList = movieVideosList;
    }

    public List<MovieVideos> getMovieVideosList() {
        return movieVideosList;
    }

    @NonNull
    @Override
    public String toString() {
        return "MoviesVideosList{" +
                "movieVideosList=" + movieVideosList +
                '}';
    }
}
