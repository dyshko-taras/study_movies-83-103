package com.dyshkotaras.movies;

import com.google.gson.annotations.SerializedName;

public class AuthorDetails {



    @SerializedName("avatar_path")
    private String avatarPath;

    @SerializedName("rating")
    private double rating;

    @Override
    public String toString() {
        return "AuthorDetails{" +
                "avatarPath='" + avatarPath + '\'' +
                ", rating=" + rating +
                '}';
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public double getRating() {
        return rating;
    }
}
