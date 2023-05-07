package com.dyshkotaras.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {
    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        loadGenres();
        movieDao = MovieDataBase.getInstance(application).movieDao();

    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Genre>> genresLD = new MutableLiveData<>();
    private final MutableLiveData<List<Trailer>> trailersLD = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> reviewsLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isAdded = new MutableLiveData<>(false);

    private MovieDao movieDao;

    private static final String TAG = "MovieDetailModel";
    private static final String SITE_YOUTUBE = "YouTube";


    public LiveData<List<Genre>> getGenresLD() {
        return genresLD;
    }

    public LiveData<List<Trailer>> getTrailersLD() {
        return trailersLD;
    }

    public LiveData<List<Review>> getReviewsLD() {
        return reviewsLD;
    }

    public LiveData<Movie> getFavouriteMovie(int movieId) {
        return movieDao.getFavouriteMovies(movieId);
    }




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

    public void loadTrailers(int id) {
        Log.d(TAG, String.valueOf(id));
        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerList, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerList trailerList) throws Throwable {
                        return trailerList.getTrailerList();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailers) throws Throwable {
//                        Log.d(TAG, trailers.toString());
                        for (Iterator<Trailer> iterator = trailers.listIterator(); iterator.hasNext(); ) {
                            String site = iterator.next().getSite();
                            if (!site.equals(SITE_YOUTUBE)) {
                                iterator.remove();
                            }
                        }
                        Collections.reverse(trailers);
                        trailersLD.setValue(trailers);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadReviews(int id) {
        Disposable disposable = ApiFactory.apiService.loadReviews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewList, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewList reviewList) throws Throwable {
                        return reviewList.getReviews();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> reviews) throws Throwable {
                        reviewsLD.setValue(reviews);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG,throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void addFavouriteMovies(Movie movie) {
        Disposable disposable = movieDao.addFavouriteMovies(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void removeFavouriteMovies(Movie movie) {
        Disposable disposable = movieDao.removeFavouriteMovies(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public List<String> getGenresMovie(Movie movie) {
        List<Integer> movieGenreIds = movie.getGenreIds();
        List<String> movieGenre = new ArrayList<>();
        if (genresLD.getValue() != null) {
            for (int i = 0; i < genresLD.getValue().size(); i++) {
                for (int j = 0; j < movieGenreIds.size(); j++) {
                    if (genresLD.getValue().get(i).getId() == movieGenreIds.get(j)) {
                        movieGenre.add(genresLD.getValue().get(i).getName());
                    }
                }
            }
        }
        return movieGenre;
    }

    public String getTextGenres(Movie movie) {
        return String.format("Genre: %s.", String.join(", ", getGenresMovie(movie)).toLowerCase());
    }
}
