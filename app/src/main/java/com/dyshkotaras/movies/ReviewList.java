package com.dyshkotaras.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewList {
    @SerializedName("results")
    private  List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "ReviewList{" +
                "reviews=" + reviews +
                '}';
    }
}
