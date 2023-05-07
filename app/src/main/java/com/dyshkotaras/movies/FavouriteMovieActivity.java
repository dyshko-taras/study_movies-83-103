package com.dyshkotaras.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavouriteMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movie);
        FavouriteMovieViewModel viewModel = new ViewModelProvider(this)
                .get(FavouriteMovieViewModel.class);
        RecyclerView recyclerViewFavouriteMovies = findViewById(R.id.recyclerViewFavouriteMovies);
        FavouriteMoviesAdapter favouriteMoviesAdapter = new FavouriteMoviesAdapter(new FavouriteMoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavouriteMovieActivity.this, movie);
                startActivity(intent);
            }
        });
        viewModel.getAllFavouriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                favouriteMoviesAdapter.setMovies(movies);
            }
        });

        recyclerViewFavouriteMovies.setAdapter(favouriteMoviesAdapter);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteMovieActivity.class);
    }
}