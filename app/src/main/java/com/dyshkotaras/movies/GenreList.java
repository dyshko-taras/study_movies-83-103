package com.dyshkotaras.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreList {
    @SerializedName("genres")
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "GenreList{" +
                "genres=" + genres +
                '}';
    }
}
