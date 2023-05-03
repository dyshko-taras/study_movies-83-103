package com.dyshkotaras.movies;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerList {

    @SerializedName("results")
    private final List<Trailer> trailerList;

    public TrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    @NonNull
    @Override
    public String toString() {
        return "MoviesVideosList{" +
                "movieVideosList=" + trailerList +
                '}';
    }
}
