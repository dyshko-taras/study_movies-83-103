package com.dyshkotaras.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private RecyclerView recyclerViewMovies;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBarLoading;

    private Button buttonPrevious;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        moviesAdapter = new MoviesAdapter(new MoviesAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                mainViewModel.uploadData();
            }
        });
        recyclerViewMovies.setAdapter(moviesAdapter);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mainViewModel = new ViewModelProvider(MainActivity.this).get(MainViewModel.class);
        mainViewModel.getMoviesListLD().observe(MainActivity.this,
                new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        moviesAdapter.setMovies(movies);
                    }
                });
        mainViewModel.getVisibleProgressBar().observe(MainActivity.this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isVisible) {
                        if (isVisible) {
                            progressBarLoading.setVisibility(View.VISIBLE);
                        } else {
                            progressBarLoading.setVisibility(View.GONE);
                        }
                    }
                });
        mainViewModel.loadMovies();
        mainViewModel.getPage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mainViewModel.loadMovies();
            }
        });
//        buttonPrevious.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mainViewModel.pagePrevious();
//            }
//        });
//        buttonNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mainViewModel.pageNext();
//            }
//        });


    }

    private void initView() {
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        progressBarLoading = findViewById(R.id.progressBarLoading);
//        buttonPrevious = findViewById(R.id.buttonPrevious);
//        buttonNext = findViewById(R.id.buttonNext);
    }
}