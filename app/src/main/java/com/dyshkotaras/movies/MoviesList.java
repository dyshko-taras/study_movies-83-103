package com.dyshkotaras.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesList {

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MoviesList{" +
                "movies=" + movies +
                '}';
    }
}
