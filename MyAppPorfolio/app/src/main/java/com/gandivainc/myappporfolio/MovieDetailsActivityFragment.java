package com.gandivainc.myappporfolio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gandivainc.myappporfolio.model.Movie;
import com.gandivainc.myappporfolio.provider.MovieProvider;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This fragment is used withing MovieDetailsAcitivity for Phone view
 * and within MovieListActivity for Tablet view (Master/Details)
 */
public class MovieDetailsActivityFragment extends Fragment implements YouTubeThumbnailView.OnInitializedListener {

    //movie details fragment view
    View view;

    //movie trailer
    private YouTubeThumbnailLoader youTubeThumbnailLoader;
    private YouTubeThumbnailView thumbnailView;

    public MovieDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        TextView movieNotSelectedView = (TextView) view.findViewById(R.id.movie_not_selected);

        //check if movie selected in List view
        if (getActivity().getIntent().getExtras() != null) {
            //movie selected so show data
            movieNotSelectedView.setVisibility(View.GONE);
            Movie movie = (Movie) (getActivity().getIntent().getExtras().getSerializable(Movie.MODEL_NAME));
            populateView(movie);
        } else {
            //movie not selected so show message
            movieNotSelectedView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    /**
     * This method is responsible for populating controls in details view.
     * The reason data population logic is separated here is to support
     * Phone view and Tablet view (Master/Details)
     * @param movie
     */
    public void populateView(Movie movie) {

        //take care of scenario when change filter from favourite movie to any other
        //and no movie was set as favourite. no need to show "movie_not_selected"
        TextView movieNotSelectedView = (TextView) view.findViewById(R.id.movie_not_selected);
        movieNotSelectedView.setVisibility(View.GONE);

        TextView title = (TextView) view.findViewById(R.id.movie_details_title);
        TextView synopsis = (TextView) view.findViewById(R.id.movie_details_synopsis);
        TextView voteAverage = (TextView) view.findViewById(R.id.movie_details_voteaverage);
        ImageView posterView = (ImageView) view.findViewById(R.id.movie_details_poster);
        Button reviewButton = (Button) view.findViewById(R.id.movie_details_review_header);
        Button trailerButton = (Button) view.findViewById(R.id.movie_details_trailer);


        title.setText(movie.getTitle() + "   (" + movie.getRelease_date() + ")");

        //if release date is in future display complete date
        //else display year only
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date releaseDate = format.parse(movie.getRelease_date());
            Date currdate = new Date();
            if (currdate.after(releaseDate)) {
                title.setText(movie.getTitle() + "   (" + movie.getRelease_date().substring(0, 4) + ")");
            }
        } catch (ParseException e) {
            Log.e("Date parse ", e.getMessage());
        }

        synopsis.setText(movie.getOverview());
        voteAverage.setText(movie.getVote_average() + "/10");

        String imageUrl = Movie.URL_BASE_MOVIE_POSTER + movie.getPoster_path();
        Picasso.with(getContext()).load(imageUrl).into(posterView);

        reviewButton.setTag(movie.getId());
        trailerButton.setTag(movie);

        MovieProvider movieDAO = new MovieProvider(this.getContext());
        Button favouriteButton = (Button) view.findViewById(R.id.movie_details_favourite);
        favouriteButton.setTag(movie);

        //Favourite button text toggle
        if (movieDAO.isMovieFavourite(movie.getId())) {
            favouriteButton.setText("Remove From Favourite");
        } else {
            favouriteButton.setText("Add To Favourite");
        }
    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

        this.youTubeThumbnailLoader = youTubeThumbnailLoader;
        this.youTubeThumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailListener());

        youTubeThumbnailLoader.setVideo("a4NT5iBFuZs");
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
        String errorMessage =
                String.format("onInitializationFailure (%1$s)",
                        youTubeInitializationResult.toString());
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView thumbnail, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView thumbnail,
                                     YouTubeThumbnailLoader.ErrorReason reason) {
            Toast.makeText(getContext(),
                    "onThumbnailError", Toast.LENGTH_SHORT).show();
        }
    }

}
