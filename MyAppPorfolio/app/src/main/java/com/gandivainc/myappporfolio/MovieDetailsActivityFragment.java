package com.gandivainc.myappporfolio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandivainc.myappporfolio.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsActivityFragment extends Fragment {

    public MovieDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        Movie movie = (Movie)(getActivity().getIntent().getExtras().getSerializable(Movie.MODEL_NAME));
        TextView title = (TextView)view.findViewById(R.id.movie_details_title);
        TextView releaseDate = (TextView)view.findViewById(R.id.movie_details_releasedate);
        TextView synopsis = (TextView)view.findViewById(R.id.movie_details_synopsis);
        TextView voteAverage = (TextView)view.findViewById(R.id.movie_details_voteaverage);
        ImageView posterView = (ImageView)view.findViewById(R.id.movie_details_poster);

        title.setText(movie.getTitle());
        releaseDate.setText(movie.getRelease_date());
        synopsis.setText(movie.getOverview());
        voteAverage.setText(movie.getVote_average() + "/10");
        String imageUrl = Movie.URL_BASE_MOVIE_POSTER+movie.getPoster_path();
        Picasso.with(getContext()).load(imageUrl).into(posterView);

        return view;
    }
}
