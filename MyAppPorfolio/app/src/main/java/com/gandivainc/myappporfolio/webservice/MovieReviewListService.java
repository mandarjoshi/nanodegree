package com.gandivainc.myappporfolio.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.gandivainc.myappporfolio.adapter.MovieReviewListAdapter;
import com.gandivainc.myappporfolio.dialog.CustomListDialog;
import com.gandivainc.myappporfolio.model.MovieReviewList;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * This service is responsible for getting reviews for selected movie from IMDb
 * Created by ea8b26s on 03/08/2016.
 */
public class MovieReviewListService extends AsyncTask<ListView, Void, MovieReviewList> {

    String url;
    Context context;
    private ProgressDialog dialog;

    public MovieReviewListService(String url, Context context) {
        this.url = url;
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
    }

    @Override
    protected MovieReviewList doInBackground(ListView... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            MovieReviewList movieReviewList = restTemplate.getForObject(url, MovieReviewList.class);
            return movieReviewList;
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
    protected void onPostExecute(MovieReviewList movieReviewList) {
        if (movieReviewList.getResults() != null && movieReviewList.getResults().size() > 0) {
            MovieReviewListAdapter adapter = new MovieReviewListAdapter(movieReviewList.getResults(), context);
            CustomListDialog customListDialog = new CustomListDialog(context, adapter);
            customListDialog.show();
        } else {
            Toast.makeText(context, "No reviews to show for this movie", Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }
}
