package com.dyshkotaras.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

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
    private MovieDetailModel movieDetailModel;
    private static final String TAG = "MovieDetailModel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        movieDetailModel = new ViewModelProvider(this).get(MovieDetailModel.class);
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this).load(BASE_URL_iMAGE + movie.getBackdropPath()).into(imageViewPoster);
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        movieDetailModel.getGenres().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genres) {
                textViewGenres.setText(movieDetailModel.getTextGenres(movie));
            }
        });
        textViewReleaseDate.setText(String.format("Release date: %s", movie.getReleaseDate()));
        textViewOriginalLanguage.setText(String.format("Original language: %s", movie.getOriginalLanguage()));
        textViewOverview.setText(String.format("   %s", movie.getOverview()));
        Toast.makeText(this,textViewOverview.getText(),Toast.LENGTH_SHORT).show();
        textViewVoteAverage.setText(String.format("Vote average: %s", movie.getVoteAverage()));
        textViewVoteCount.setText(String.format("Vote count: %s", movie.getVoteCount()));
        textViewPopularity.setText(String.format("Popularity: %s", movie.getPopularity()));
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