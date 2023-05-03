package com.dyshkotaras.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.Holder> {

    private List<Trailer> trailerList = new ArrayList<>();
    private OnTrailerClickListener onTrailerClickListener;

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }

    public TrailerAdapter(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_item,
                parent,
                false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Trailer trailer = trailerList.get(position);
        holder.textViewNameTrailer.setText(String.format("%s - %s - %s",
                trailer.getType(), trailer.getSite(), trailer.getName()));
        holder.textViewNameTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTrailerClickListener != null) {
                    onTrailerClickListener.onTrailerClick(trailer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final TextView textViewNameTrailer;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewNameTrailer = itemView.findViewById(R.id.textViewNameTrailer);
        }
    }


    interface OnTrailerClickListener {
        void onTrailerClick(Trailer trailer);
    }
}
