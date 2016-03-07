package com.gandivainc.myappporfolio.webservice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.gandivainc.myappporfolio.MovieListActivity;
import com.gandivainc.myappporfolio.R;
import com.gandivainc.myappporfolio.adapter.MovieListAdapter;
import com.gandivainc.myappporfolio.model.MovieList;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mandar on 2/23/2016.
 */
public class MovieListService extends AsyncTask<GridView, Void, MovieList>{

    private ProgressDialog dialog;
    GridView gridView;
    String url;

    public MovieListService(MovieListActivity movieListActivity, String url)
    {
        this.url = url;
        dialog = new ProgressDialog(movieListActivity);
        dialog.setTitle("Loading");
        dialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
    }

    @Override
    protected MovieList doInBackground(GridView... params) {
        try {
            this.gridView = params[0];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            MovieList movieList = restTemplate.getForObject(url, MovieList.class);
            return movieList;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(MovieList movieList) {
        MovieListAdapter adapter = new MovieListAdapter(this.gridView.getContext(), R.layout.movie_list_item,movieList.getResults());
        gridView.setAdapter(adapter);
        dialog.dismiss();
    }
}
