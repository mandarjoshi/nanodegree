package com.gandivainc.myappporfolio.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gandivainc.myappporfolio.adapter.MovieTrailerAdapter;
import com.gandivainc.myappporfolio.dialog.CustomListDialog;
import com.gandivainc.myappporfolio.model.Movie;
import com.gandivainc.myappporfolio.model.MovieVideoList;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * This service is responsible for getting trailers for selected movie from IMDb
 * Created by ea8b26s on 04/18/2016.
 */
public class MovieVideoListService extends AsyncTask<Void, Void, MovieVideoList> {

    String url;
    Context context;
    private ProgressDialog dialog;
    private Movie movie;

    public MovieVideoListService(String url, Context context, Movie movie) {
        this.movie = movie;
        this.url = url;
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
    }

    @Override
    protected MovieVideoList doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            MovieVideoList movieVideoList = restTemplate.getForObject(url, MovieVideoList.class);
            return movieVideoList;
        } catch (Exception e) {
            Log.e("MovieDetailsActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
    }

    @Override
    protected void onPostExecute(MovieVideoList movieVideoList) {
        if (movieVideoList.getResults() != null && movieVideoList.getResults().size() > 0) {
            MovieTrailerAdapter adapter = new MovieTrailerAdapter(movieVideoList.getResults(), context,movie);
            CustomListDialog customListDialog = new CustomListDialog(context, adapter);
            customListDialog.doShareUrl();
            customListDialog.show();
        } else {
            Toast.makeText(context, "No reviews to show for this movie", Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }

}
