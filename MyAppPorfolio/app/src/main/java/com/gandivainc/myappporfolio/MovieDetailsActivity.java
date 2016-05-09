package com.gandivainc.myappporfolio;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This activity is responsible for showing details for selected movie.
 */
public class MovieDetailsActivity extends MovieMultiViewActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

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
