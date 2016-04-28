package com.gandivainc.myappporfolio.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gandivainc.myappporfolio.R;
import com.gandivainc.myappporfolio.model.Movie;
import com.gandivainc.myappporfolio.model.MovieVideo;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * This class is responsible for populating trailers for selected movie.
 * Created by ea8b26s on 04/17/2016.
 */
public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.VideoInfoHolder> {

    List<MovieVideo> movieVideos;
    Context ctx;
    //StringBuffer shareUrl = new StringBuffer("https://www.youtube.com/watch?v=");
    MovieVideo shareTrailer = null;
    Movie movie = null;

    public MovieTrailerAdapter(List<MovieVideo> movieVideos, Context context, Movie movie) {
        this.ctx = context;
        this.movie = movie;
        this.movieVideos = movieVideos;
        if(this.movieVideos != null && this.movieVideos.size() > 0)
        {
            //set first trailer to share
            shareTrailer = movieVideos.get(0);
        }
    }

    public MovieVideo getShareTrailer() {
        return shareTrailer;
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_trailer_list_item, parent, false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {


        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        holder.youTubeThumbnailView.initialize(Movie.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(movieVideos.get(position).getKey());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieVideos.size();
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        protected ImageView playButton;
        YouTubeThumbnailView youTubeThumbnailView;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {
            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, Movie.YOUTUBE_API_KEY, movieVideos.get(getLayoutPosition()).getKey());
            ctx.startActivity(intent);
        }
    }
}