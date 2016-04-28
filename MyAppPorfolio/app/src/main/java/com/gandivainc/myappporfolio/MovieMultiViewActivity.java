package com.gandivainc.myappporfolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gandivainc.myappporfolio.model.Movie;
import com.gandivainc.myappporfolio.model.MovieReview;
import com.gandivainc.myappporfolio.model.MovieVideo;
import com.gandivainc.myappporfolio.provider.MovieProvider;
import com.gandivainc.myappporfolio.webservice.MovieReviewListService;
import com.gandivainc.myappporfolio.webservice.MovieVideoListService;

/**
 * This activity is responsible for implementing common functionality
 * for both Phone view and Tablet view (Master/Details)
 * Created by ea8b26s on 04/24/2016.
 */
public class MovieMultiViewActivity extends AppCompatActivity {

    boolean favouriteStatusChanged = false;
    String movieName;

    /**
     * This method is responsible for displaying movie trailers
     * on popup dialog. This method is called when user selects
     * trailer on details screen.
     * @param view
     */
    public void launchMovieTrailer(View view) {
        Movie movie = (Movie) view.getTag();
        String url = Movie.getMovieVideoURL(movie.getId());
        MovieVideoListService service = new MovieVideoListService(url, this, movie);
        service.execute();
    }

    /**
     * This method is responsible for displaying movie reviews
     * on popup dialog. This method is called when user selects
     * reviews on details screen.
     * @param view
     */
    public void launchMovieReview(View view) {
        String url = MovieReview.REVIEW_URL + view.getTag() + "/reviews?api_key=" + Movie.API_KEY;
        MovieReviewListService service = new MovieReviewListService(url, this);
        service.execute();
    }

    /**
     * This method is responsible for adding or removing movie from favourites list
     * This method is called when user selects favourites button on detail screen.
     * @param view
     */
    public void maintainFavourite(View view) {
        MovieProvider movieDAO = new MovieProvider(this);
        Movie movie = (Movie) view.getTag();

        Button reviewButton = (Button) findViewById(R.id.movie_details_favourite);

        //mark Favourite toggle
        if (movieDAO.isMovieFavourite(movie.getId())) {
            //movie already marked as favourite so remove from favourite list
            if (movieDAO.removeFavouriteMovie(movie.getId())) {
                Toast.makeText(this, "Movie removed from favourites", Toast.LENGTH_SHORT).show();
                reviewButton.setText("Add Favourites");
                favouriteStatusChanged = true;
            } else {
                Toast.makeText(this, "Couldn't removed movie from favourites", Toast.LENGTH_SHORT).show();
            }
        } else {
            //mark movie as favourite
            if (movieDAO.addFavouriteMovie(movie)) {
                Toast.makeText(this, "Movie added to favourites", Toast.LENGTH_SHORT).show();
                reviewButton.setText("Remove Favourites");
                favouriteStatusChanged = true;
            } else {
                Toast.makeText(this, "Couldn't add Movie to favourites", Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     * This method is responsible for sharing URL for first trailer.
     * @param view
     */
    public void shareUrl(View view) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        MovieVideo trailer = (MovieVideo) view.getTag(R.id.TRAILER_OBJECT);
        Movie movie = (Movie) view.getTag(R.id.MOVIE_OBJECT);
        share.putExtra(Intent.EXTRA_SUBJECT, "Check out trailer for "+movie.getTitle());
        share.putExtra(Intent.EXTRA_TEXT, trailer.getUrl());

        startActivity(Intent.createChooser(share, "Share trailer!"));
    }

}

