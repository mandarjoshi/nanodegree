package com.gandivainc.myappporfolio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gandivainc.myappporfolio.model.Movie;
import com.gandivainc.myappporfolio.model.MovieVideo;

/**
 * This activity is responsible for showing details for selected movie.
 */
public class MovieDetailsActivity extends MovieMultiViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //now set custom
        //moved share link to trailer pop up dialog since it is more appropriate there
        //I will delete this code once my change is accepted

        /*
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_movie_details_actionbar, null);

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(false);
        */

    }

}
