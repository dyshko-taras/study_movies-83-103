package com.dyshkotaras.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainActivity";

    private final MutableLiveData<List<Movie>> moviesListLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> page = new MutableLiveData<>(1);


    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

//    private int page = 1;


    public MainViewModel(@NonNull Application application) {
        super(application);
        loadMovies();
    }

    public LiveData<List<Movie>> getMoviesListLD() {
        return moviesListLD;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<Integer> getPage() {
        return page;
    }

    public void loadMovies() {
        Boolean loading = isLoading.getValue();
        if (loading != null && loading) return;
        Log.d(TAG, String.valueOf(page.getValue()));
        Disposable disposable = ApiFactory.apiService.loadMovies(page.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<MoviesList>() {
                    @Override
                    public void accept(MoviesList moviesList) throws Throwable {
                        //page++;
                        if (moviesListLD.getValue() == null ) {
                            moviesListLD.setValue(moviesList.getMovies());
                        } else {
                            List<Movie> list = moviesListLD.getValue();
                            list.addAll(moviesList.getMovies());
                            moviesListLD.setValue(list);
                        }
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

    public void uploadData() {
        if (page.getValue() != null) {
            int pages = page.getValue() + 1;
            page.setValue(pages);
        }
    }

//    public void pagePrevious() {
//        if (page.getValue() > 1) page.setValue(page.getValue() - 1);
//    }
//
//    public void pageNext() {
//        page.setValue(page.getValue() + 1);
//    }
//
}
