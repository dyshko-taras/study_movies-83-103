package com.dyshkotaras.movies;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class MovieVideos {

    @SerializedName("name")
    private final String name;

    @SerializedName("type")
    private final String type;

    @SerializedName("site")
    private final String site;

    @SerializedName("key")
    private final String key;

    public MovieVideos(String name, String type, String site, String key) {
        this.name = name;
        this.type = type;
        this.site = site;
        this.key = key;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieVideos{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", site='" + site + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }

    public String getKey() {
        return key;
    }
}
