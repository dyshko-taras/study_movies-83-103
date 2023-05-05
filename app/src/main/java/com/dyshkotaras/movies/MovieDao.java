package com.dyshkotaras.movies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM FAVOURITE_MOVIE")
    LiveData<List<Movie>> getAllFavouriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addFavouriteMovies(Movie movie);

    @Query("DELETE FROM FAVOURITE_MOVIE WHERE id = :movieId")
    Completable removeFavouriteMovies(int movieId);

    @Query("SELECT * FROM FAVOURITE_MOVIE WHERE id = :movieId")
    LiveData<Movie> getFavouriteMovies(int movieId);
}
