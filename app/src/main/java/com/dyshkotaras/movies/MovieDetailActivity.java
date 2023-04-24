package com.dyshkotaras.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    //init views
    private ImageView imageViewPoster;
    private TextView textViewOriginalTitle;
    private TextView textViewGenres;
    private TextView textViewReleaseDate;
    private TextView textViewOriginalLanguage;
    private TextView textViewOverview;
    private TextView textViewVoteAverage;
    private TextView textViewVoteCount;
    private TextView textViewPopularity;

    private static final String EXTRA_MOVIE = "movie";
    private String BASE_URL_iMAGE = "https://image.tmdb.org/t/p/w500";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        Glide.with(this).load(BASE_URL_iMAGE + movie.getBackdropPath()).into(imageViewPoster);
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewGenres.setText(movie.getGenreIds().toString());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewOriginalLanguage.setText(movie.getOriginalLanguage());
        textViewOverview.setText(movie.getOverview());
        textViewVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        textViewVoteCount.setText(String.valueOf(movie.getVoteCount()));
        textViewPopularity.setText(String.valueOf(movie.getPopularity()));
    }

    // methods init views
    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewBackdrop);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewGenres = findViewById(R.id.textViewGenres);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewOriginalLanguage = findViewById(R.id.textViewOriginalLanguage);
        textViewOverview = findViewById(R.id.textViewOverview);
        textViewVoteAverage = findViewById(R.id.textViewVoteAverage);
        textViewVoteCount = findViewById(R.id.textViewVoteCount);
        textViewPopularity = findViewById(R.id.textViewPopularity);
    }

    // methods new intent
    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }



}