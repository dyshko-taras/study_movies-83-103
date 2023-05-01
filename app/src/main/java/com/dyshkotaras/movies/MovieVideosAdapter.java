package com.dyshkotaras.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.ViewHolder> {

    private List<MovieVideos> movieVideosList = new ArrayList<>();

    public void setMovieVideosList(List<MovieVideos> movieVideosList) {
        this.movieVideosList = movieVideosList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_video_item,
                parent,
                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieVideos movieVideos = movieVideosList.get(position);
        holder.textViewNameVideo.setText(String.format("%s - %s - %s",
                movieVideos.getType(), movieVideos.getSite(), movieVideos.getName()));
    }

    @Override
    public int getItemCount() {
        return movieVideosList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewNameVideo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameVideo = itemView.findViewById(R.id.textViewNameVideo);
        }
    }
}
