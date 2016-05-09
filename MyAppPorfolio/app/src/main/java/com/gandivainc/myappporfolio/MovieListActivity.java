package com.gandivainc.myappporfolio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gandivainc.myappporfolio.adapter.MovieListAdapter;
import com.gandivainc.myappporfolio.model.Movie;
import com.gandivainc.myappporfolio.model.MovieList;
import com.gandivainc.myappporfolio.provider.MovieProvider;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends MovieMultiViewActivity {

    private int page = 1;
    private MovieListAdapter adapter;
    private boolean loading = false;
    private ProgressDialog progress;
    private String sortBy;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.movie_list_no_data) TextView noData;
    @BindView(R.id.movie_list_gridview) GridView gridView;
    @BindView(R.id.prgBarBottom) LinearLayout bottomBar;
    @BindView(R.id.prgBarTop) LinearLayout topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress = new ProgressDialog(this);
        progress.setMessage("loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        bottomBar.setVisibility(View.GONE);
        topBar.setVisibility(View.GONE);

        //Support Phone view and Tablet view (Master/Details)
        MovieDetailsActivityFragment fragement = (MovieDetailsActivityFragment) getSupportFragmentManager().findFragmentById(R.id.movie_details_fragment);
        adapter = new MovieListAdapter(this, R.layout.movie_list_item, new ArrayList<Movie>(), fragement);

        loadNewData(page);
        gridView.setAdapter(adapter);

        //Display header depending for sort/filter type
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        sortBy = prefs.getString(getResources().getString(R.string.movie_sort_by), "popularity.desc");
        if (sortBy.equalsIgnoreCase("popularity.desc")) {
            getSupportActionBar().setTitle("Popular Movies");
        } else if (sortBy.equalsIgnoreCase("vote_average.desc")) {
            getSupportActionBar().setTitle("Highest Rated Movies");
        } else {
            getSupportActionBar().setTitle("Favourite Movies");
        }

        //On scroll for paging data
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (ifloadNext(firstVisibleItem, visibleItemCount, totalItemCount)) {
                    //all favourites movies displayed on one page no paging required
                    //for other paging required
                    if (!sortBy.equalsIgnoreCase("favourites")) {
                        bottomBar.setVisibility(View.VISIBLE);
                    }
                } else {
                    bottomBar.setVisibility(View.GONE);
                }
                if (ifloadPrev(firstVisibleItem, visibleItemCount, totalItemCount)) {
                    if (!sortBy.equalsIgnoreCase("favourites")) {
                        topBar.setVisibility(View.VISIBLE);
                    }
                } else {
                    topBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    /**
     * This method is responsible to check if user has scrolled to bottom of page and
     * if next button should be displayed at bottom of page for paging
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     * @return
     */
    private boolean ifloadNext(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        return visibleItemCount < 10 && (firstVisibleItem + visibleItemCount == totalItemCount) && !loading;
    }

    /**
     * This method is responsible to check if user has scrolled to top of page and
     * if previous button should be displayed at top of page for paging
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     * @return
     */
    private boolean ifloadPrev(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        return page > 1 && firstVisibleItem == 0 && !loading;
    }

    /**
     * This method is responsible for calling load data for next page
     * @param view
     */
    public void loadPrevPage(View view) {
        loadNewData(--page);
    }

    /**
     * This method is responsible for calling load data for previous page
     * @param view
     */
    public void loadNextPage(View view) {
        loadNewData(++page);
    }

    /**
     * This method is responsible for calling service to get movie data.
     * @param page
     */
    private void loadNewData(int page) {
        progress.show();
        loading = true;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        sortBy = prefs.getString(getResources().getString(R.string.movie_sort_by), "popularity.desc");

        String url = Movie.getMovieListURL(page, sortBy);
        (new MovieService()).execute(url);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String currSortBy = prefs.getString(getResources().getString(R.string.movie_sort_by), "popularity.desc");
        if (!currSortBy.equalsIgnoreCase(sortBy)) {
            //If user changed sorting using settings menu reload page since
            //it is declared as android:launchMode="singleTop"
            recreate();
        }

    }

    /**
     * This service is responsible to retrieve movie data from IMDb services.
     */
    private class MovieService extends AsyncTask<String, Void, String> {

        //store newly retrieved list
        List<Movie> newList = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                //favourites are stored in local database
                //no need to call services
                if (sortBy != null && !sortBy.equalsIgnoreCase("favourites")) {
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    MovieList movieList = restTemplate.getForObject(params[0], MovieList.class);
                    newList = movieList.getResults();
                } else {
                    MovieProvider movieDAO = new MovieProvider(getApplicationContext());
                    newList = movieDAO.getFavouriteMovies();

                }
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //if no data display message
            adapter.clear();
            if (newList == null || newList.size() == 0) {
                if (sortBy != null && !sortBy.equalsIgnoreCase("favourites")) {
                    Toast.makeText(getApplicationContext(), "Some error occured. Can not get data at this time.", Toast.LENGTH_LONG).show();
                    noData.setVisibility(View.GONE);
                }else {
                    noData.setVisibility(View.VISIBLE);
                }

            } else {
                noData.setVisibility(View.GONE);
                //set new data
                adapter.addAll(newList);
                adapter.notifyDataSetChanged();

                //reset to top position
                bottomBar.setVisibility(View.GONE);
                gridView.smoothScrollToPosition(0);
            }

            loading = false;
            progress.dismiss();

        }
    }

}
