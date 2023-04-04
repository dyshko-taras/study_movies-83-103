package com.dyshkotaras.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(MainActivity.this).get(MainViewModel.class);
        mainViewModel.getMoviesListLD().observe(MainActivity.this,
                new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        Log.d(TAG,movies.toString());
                    }
                });
        mainViewModel.loadMovies();
    }
}