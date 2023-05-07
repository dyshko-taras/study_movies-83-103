package com.dyshkotaras.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMoviesAdapter extends RecyclerView.Adapter<FavouriteMoviesAdapter.MovieViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private List<Movie> movies = new ArrayList<>();
    private String BASE_URL_iMAGE = "https://image.tmdb.org/t/p/w500";
    private static final String TAG = "FavouriteMoviesAdapter";


    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public FavouriteMoviesAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false
        );
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        Movie movie = movies.get(position);
        movieViewHolder.textViewVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        int circleResId;
        if (movie.getVoteAverage() > 7) {
            circleResId = R.drawable.circle_green;
        } else if (movie.getVoteAverage() > 5) {
            circleResId = R.drawable.circle_orange;
        } else {
            circleResId = R.drawable.circle_red;
        }
        movieViewHolder.textViewVoteAverage.setBackgroundResource(circleResId);
        Glide.with(movieViewHolder.itemView)
                .load(BASE_URL_iMAGE + movie.getPosterPath())
                .into(movieViewHolder.imageViewPoster);
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewPoster;
        private final TextView textViewVoteAverage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewBackdrop);
            textViewVoteAverage = itemView.findViewById(R.id.textViewVoteAverage);
        }
    }

    interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

}


