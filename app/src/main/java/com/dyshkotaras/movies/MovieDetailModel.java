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
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailModel extends AndroidViewModel {
    public MovieDetailModel(@NonNull Application application) {
        super(application);
        loadGenres();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Genre>> genres = new MutableLiveData<>();

    public LiveData<List<Genre>> getGenres() {
        return genres;
    }

    private static final String TAG = "MovieDetailModel1";

    public void loadGenres() {
        Disposable disposable = ApiFactory.apiService.loadGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenreList>() {
                    @Override
                    public void accept(GenreList genreList) throws Throwable {
                        genres.setValue(genreList.getGenres());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, "ERROR");
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
        for (int i = 0; i < genres.getValue().size();i++) {
            for(int j = 0; j < movieGenreIds.size(); j++) {
                if (genres.getValue().get(i).getId() == movieGenreIds.get(j)) {
                    movieGenre.add(genres.getValue().get(i).getName());
                }
            }
        }
        return movieGenre;
    }

    public String getTextGenres(Movie movie) {
        return String.format("Genre: %s.",String.join(", ",getGenresMovie(movie)).toLowerCase());
    }
}
