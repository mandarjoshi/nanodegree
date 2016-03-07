package com.gandivainc.myappporfolio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.gandivainc.myappporfolio.MovieDetailsActivity;
import com.gandivainc.myappporfolio.R;
import com.gandivainc.myappporfolio.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ea8b26s on 02/20/2016.
 */
public class MovieListAdapter extends ArrayAdapter<Movie>{

    public MovieListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public MovieListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public MovieListAdapter(Context context, int resource, Movie[] objects) {
        super(context, resource, objects);
    }

    public MovieListAdapter(Context context, int resource, int textViewResourceId, Movie[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public MovieListAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
    }

    public MovieListAdapter(Context context, int resource, int textViewResourceId, List<Movie> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item, parent, false);
        }
        View view = convertView;
        Movie movie = (Movie)getItem(position);
        ImageView imageView = (ImageView)view.findViewById(R.id.movie_list_item_image);
        imageView.setTag(movie);
        //imageView.setImageResource(R.drawable.download);
        String imageUrl = Movie.URL_BASE_MOVIE_POSTER+movie.getPoster_path();
        Picasso.with(getContext()).load(imageUrl).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie selectedMovie = (Movie)view.getTag();
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra(Movie.MODEL_NAME,selectedMovie);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
