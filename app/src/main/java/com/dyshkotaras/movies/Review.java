package com.dyshkotaras.movies;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("author")
    private String nameAuthor;

    @SerializedName("author_details")
    private AuthorDetails authorDetails;

    @SerializedName("created_at")
    private String dataCreation;

    @SerializedName("content")
    private String content;

    @Override
    public String toString() {
        return "Review{" +
                "nameAuthor='" + nameAuthor + '\'' +
                ", authorDetails=" + authorDetails +
                ", dataCreation='" + dataCreation + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public AuthorDetails getAuthorDetails() {
        return authorDetails;
    }

    public String getDataCreation() {
        return dataCreation;
    }

    public String getContent() {
        return content;
    }
}
