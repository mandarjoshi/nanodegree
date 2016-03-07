package com.gandivainc.myappporfolio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.gandivainc.myappporfolio.model.Movie;
import com.gandivainc.myappporfolio.webservice.MovieListService;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GridView gridView = (GridView)findViewById(R.id.movie_list_gridview);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortBy = prefs.getString(getResources().getString(R.string.movie_sort_by), "popularity.desc");

        if(sortBy.equalsIgnoreCase("popularity.desc"))
        {
            getSupportActionBar().setTitle("Popular Movies");
        }
        else
        {
            getSupportActionBar().setTitle("Rated Movies");
        }


        String url = Movie.URL_MOVIE_LIST+sortBy;
        MovieListService service = new MovieListService(this,url);
        service.execute(gridView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
