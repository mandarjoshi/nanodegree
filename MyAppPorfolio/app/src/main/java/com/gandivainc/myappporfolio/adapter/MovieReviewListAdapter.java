package com.gandivainc.myappporfolio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gandivainc.myappporfolio.R;
import com.gandivainc.myappporfolio.model.MovieReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class is responsible for populating reviews for selected movie.
 * Created by ea8b26s on 03/08/2016.
 */
public class MovieReviewListAdapter extends RecyclerView.Adapter<MovieReviewListAdapter.ViewHolder> {

    List<MovieReview> movieReviews;
    Context context;

    public MovieReviewListAdapter(List<MovieReview> movieReviews, Context context) {
        this.movieReviews = movieReviews;
        this.context = context;
    }

    @Override
    public MovieReviewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_review_list_item, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.author.setText(movieReviews.get(position).getAuthor());
        holder.content.setText(movieReviews.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_details_review_item_author_header ) TextView header;
        @BindView(R.id.movie_details_review_item_author) TextView author;
        @BindView(R.id.movie_details_review_item_content) TextView content;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
