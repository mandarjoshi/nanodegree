package com.gandivainc.myappporfolio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.gandivainc.myappporfolio.MovieDetailsActivity;
import com.gandivainc.myappporfolio.MovieDetailsActivityFragment;
import com.gandivainc.myappporfolio.R;
import com.gandivainc.myappporfolio.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * This class is responsible for populating movie list.
 * Created by ea8b26s on 02/20/2016.
 */
public class MovieListAdapter extends ArrayAdapter<Movie> {

    //Support Phone view and Tablet view using fregment
    MovieDetailsActivityFragment fragment;

    public MovieListAdapter(Context context, int resource, List<Movie> objects, MovieDetailsActivityFragment fragment) {
        super(context, resource, objects);
        this.fragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item, parent, false);
        }

        View view = convertView;

        Movie movie = (Movie) getItem(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.movie_list_item_image);
        imageView.setTag(movie);
        String imageUrl = Movie.URL_BASE_MOVIE_POSTER + movie.getPoster_path();
        Picasso.with(getContext()).load(imageUrl).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie selectedMovie = (Movie) view.getTag();
                if (fragment == null) {
                    //This is Phone view. Details will be displayed on new page.
                    Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                    intent.putExtra(Movie.MODEL_NAME, selectedMovie);
                    getContext().startActivity(intent);
                } else {
                    //This is Tablet view (Master/Details), details will be displayed in Details view on same page.
                    fragment.populateView(selectedMovie);
                }
            }
        });

        //For Tablet view (Master/Details) set details of first movie
        if (position == 0 && fragment != null) {
            fragment.populateView(movie);
        }

        return convertView;
    }
}
