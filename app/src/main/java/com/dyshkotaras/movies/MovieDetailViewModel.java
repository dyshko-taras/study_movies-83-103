package com.dyshkotaras.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {
    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        loadGenres();
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Genre>> genresLD = new MutableLiveData<>();
    private final MutableLiveData<List<MovieVideos>> movieVideosLD = new MutableLiveData<>();

    public LiveData<List<Genre>> getGenresLD() {
        return genresLD;
    }

    public LiveData<List<MovieVideos>> getMovieVideosLD() {
        return movieVideosLD;
    }

    private static final String TAG = "MovieDetailModel1";

    private void loadGenres() {
        Disposable disposable = ApiFactory.apiService.loadGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GenreList, List<Genre>>() {
                    @Override
                    public List<Genre> apply(GenreList genreList) throws Throwable {
                        return genreList.getGenres();
                    }
                })
                .subscribe(new Consumer<List<Genre>>() {
                    @Override
                    public void accept(List<Genre> genres) throws Throwable {
                        genresLD.setValue(genres);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadMovieVideos(int id) {
        Log.d(TAG, String.valueOf(id));
        Disposable disposable = ApiFactory.apiService.loadMovieVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MoviesVideosList, List<MovieVideos>>() {
                    @Override
                    public List<MovieVideos> apply(MoviesVideosList moviesVideosList) throws Throwable {
                        return moviesVideosList.getMovieVideosList();
                    }
                })
                .subscribe(new Consumer<List<MovieVideos>>() {
                    @Override
                    public void accept(List<MovieVideos> movieVideos) throws Throwable {
                        Log.d(TAG, movieVideos.toString());
                        movieVideosLD.setValue(movieVideos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public List<String> getGenresMovie(Movie movie) {
        List<Integer> movieGenreIds = movie.getGenreIds();
        List<String > movieGenre = new ArrayList<>();
        if (genresLD.getValue() != null) {
            for (int i = 0; i < genresLD.getValue().size(); i++) {
                for(int j = 0; j < movieGenreIds.size(); j++) {
                    if (genresLD.getValue().get(i).getId() == movieGenreIds.get(j)) {
                        movieGenre.add(genresLD.getValue().get(i).getName());
                    }
                }
            }
        }
        return movieGenre;
    }

    public String getTextGenres(Movie movie) {
        return String.format("Genre: %s.",String.join(", ",getGenresMovie(movie)).toLowerCase());
    }
}
