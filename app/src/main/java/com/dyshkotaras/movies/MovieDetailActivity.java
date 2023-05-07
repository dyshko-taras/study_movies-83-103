package com.dyshkotaras.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    private MovieDetailViewModel viewModel;
    private RecyclerView recyclerViewTrailer;
    private TrailerAdapter trailerAdapter;
    private RecyclerView recyclerViewReview;
    private ReviewAdapter reviewAdapter;
    private ImageView imageViewStar;


    private static final String EXTRA_MOVIE = "movie";
    private static final String BASE_URL_iMAGE = "https://image.tmdb.org/t/p/w500";
    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private static final String TAG = "MovieDetailActivity1";
    public static final String YOUTUBE_PACKAGE_NAME = "com.google.android.youtube";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();

        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        viewModel.loadTrailers(movie.getId());
        viewModel.loadReviews(movie.getId());

        viewModel.getTrailersLD().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> movieVideos) {
                trailerAdapter.setTrailerList(movieVideos);
            }
        });
        viewModel.getGenresLD().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genres) {
                textViewGenres.setText(viewModel.getTextGenres(movie));
            }
        });
        viewModel.getReviewsLD().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                Log.d(TAG, reviews.toString());
                reviewAdapter.setReviews(reviews);
            }
        });
        Drawable starOff = ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_off);
        Drawable starOn = ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_on);
        viewModel.getFavouriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {
                if (movieFromDb == null) {
                    imageViewStar.setImageDrawable(starOff);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewModel.addFavouriteMovies(movie);
                        }
                    });
                } else {
                    imageViewStar.setImageDrawable(starOn);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewModel.removeFavouriteMovies(movie);
                        }
                    });
                }
            }
        });


        trailerAdapter = new TrailerAdapter(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                launchYoutube(MovieDetailActivity.this, YOUTUBE_URL + trailer.getKey());
            }
        });
        reviewAdapter = new ReviewAdapter();

        recyclerViewTrailer.setAdapter(trailerAdapter);
        recyclerViewReview.setAdapter(reviewAdapter);

        Glide.with(this).load(BASE_URL_iMAGE + movie.getBackdropPath()).into(imageViewPoster);
        textViewOriginalTitle.setText(movie.getOriginalTitle());
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
        recyclerViewTrailer = findViewById(R.id.recyclerViewTrailer);
        recyclerViewReview = findViewById(R.id.recyclerViewReview);
        imageViewStar = findViewById(R.id.imageViewStar);
    }

    // methods new intent
    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

    public static void launchYoutube(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage(YOUTUBE_PACKAGE_NAME);
        context.startActivity(intent);
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(intent);
//        } else {
//            Log.d(TAG,String.valueOf(context.getPackageManager() == null));
//            Toast.makeText(context, "YouTube app is not installed", Toast.LENGTH_SHORT).show();
//        }
    }


}