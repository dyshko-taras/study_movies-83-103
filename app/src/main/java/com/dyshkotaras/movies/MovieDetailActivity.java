package com.dyshkotaras.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    //init views
    private ImageView imageViewPoster;
    private TextView textViewOriginalTitle;
    private TextView textViewGenres;
    private TextView textViewReleaseDate;
    private TextView textViewOriginalLanguage;
    private TextView textViewAdult;
    private TextView textViewOverview;
    private TextView textViewVoteAverage;
    private TextView textViewVoteCount;
    private TextView textViewPopularity;

    private static final String EXTRA_MOVIE = "movie";
    private static final String BASE_URL_iMAGE = "https://image.tmdb.org/t/p/w500";
    private MovieDetailModel movieDetailModel;
    private static final String TAG = "MovieDetailModel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        movieDetailModel = new ViewModelProvider(this).get(MovieDetailModel.class);
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        movieDetailModel.loadMovieVideos(movie);


        movieDetailModel.getMovieVideosLD().observe(this, new Observer<List<MovieVideos>>() {
            @Override
            public void onChanged(List<MovieVideos> movieVideos) {
                Log.d("TARASS",movieVideos.toString());
            }
        });

        Glide.with(this).load(BASE_URL_iMAGE + movie.getBackdropPath()).into(imageViewPoster);
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        movieDetailModel.getGenresLD().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genres) {
                textViewGenres.setText(movieDetailModel.getTextGenres(movie));
            }
        });
        textViewReleaseDate.setText(String.format("Release date: %s", movie.getReleaseDate()));
        textViewOriginalLanguage.setText(String.format("Original language: %s", movie.getOriginalLanguage()));
        if (movie.isAdult()) {
            textViewAdult.setText("Adult: yes");
        } else {
            textViewAdult.setText("Adult: no");
        }
        textViewOverview.setText(String.format("   %s", movie.getOverview()));
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
        textViewAdult = findViewById(R.id.textViewAdult);
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