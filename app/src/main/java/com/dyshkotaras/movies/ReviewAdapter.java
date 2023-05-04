package com.dyshkotaras.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SplittableRandom;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews = new ArrayList<>();
    private static final String BASE_URL_iMAGE = "https://image.tmdb.org/t/p/w500";

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_item,
                parent,
                false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        String url = BASE_URL_iMAGE + review.getAuthorDetails().getAvatarPath();
        if (URLUtil.isValidUrl(url)) {
            Glide.with(holder.itemView)
                    .load(url)
                    .error(android.R.drawable.ic_menu_gallery)
                    .into(holder.avatarReviewImageView);
        }

        holder.nameReviewTextView.setText(review.getNameAuthor());
        holder.ratingReviewTextView.setText(String.valueOf(review.getAuthorDetails().getRating()));
        holder.textReviewTextView.setText(review.getContent());
        holder.dateReviewTextView.setText(review.getDateCreation().substring(0, 10));
        int colorRatingResId;
        if (Objects.isNull(review.getAuthorDetails().getRating()) || review.getAuthorDetails().getRating() == 0) {
            colorRatingResId = android.R.color.darker_gray;
        } else if (review.getAuthorDetails().getRating() > 7) {
            colorRatingResId = android.R.color.holo_green_light;
        } else if (review.getAuthorDetails().getRating() > 5) {
            colorRatingResId = android.R.color.holo_orange_light;
        } else {
            colorRatingResId = android.R.color.holo_red_light;
        }
        int color = ContextCompat.getColor(holder.itemView.getContext(),colorRatingResId);
        holder.reviewCardView.setCardBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final CardView reviewCardView;
        private final ImageView avatarReviewImageView;
        private final TextView nameReviewTextView;
        private final TextView ratingReviewTextView;
        private final TextView textReviewTextView;
        private final TextView dateReviewTextView;


        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewCardView = itemView.findViewById(R.id.reviewCardView);
            avatarReviewImageView = itemView.findViewById(R.id.avatarReviewImageView);
            nameReviewTextView = itemView.findViewById(R.id.nameReviewTextView);
            ratingReviewTextView = itemView.findViewById(R.id.ratingReviewTextView);
            textReviewTextView = itemView.findViewById(R.id.textReviewTextView);
            dateReviewTextView = itemView.findViewById(R.id.dateReviewTextView);
        }
    }
}
