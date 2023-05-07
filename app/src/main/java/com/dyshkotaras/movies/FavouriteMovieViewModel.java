package com.dyshkotaras.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteMovieViewModel extends AndroidViewModel {
    public FavouriteMovieViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDataBase.getInstance(application).movieDao();

    }

    private final MovieDao movieDao;

    public LiveData<List<Movie>> getAllFavouriteMovies() {
        return movieDao.getAllFavouriteMovies();
    }
}
