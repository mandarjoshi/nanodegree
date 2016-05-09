package com.gandivainc.myappporfolio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This is main activity to display list of applications.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is responsible for starting new activity for Spotify
     * steamer application. This method will be called when user selects
     * Spotify Steamer button on main page.
     * @param view
     */
    public void launchSpotifySteamer(View view) {
        Intent intent = new Intent(this, MovieListActivity.class);
        startActivity(intent);
    }

    /**
     * This method is responsible for starting new activity for Scores
     * application. This method will be called when user selects
     * Scores button on main page.
     * @param view
     */
    public void launchScoresApp(View view) {
        Toast.makeText(this, "This button will launch SCORES APP", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is responsible for starting new activity for Library
     * application. This method will be called when user selects
     * Library button on main page.
     * @param view
     */
    public void launchLibraryApp(View view) {
        Toast.makeText(this, "This button will launch LIBRARY APP", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is responsible for starting new activity for Building
     * bigger application. This method will be called when user selects
     * Building Bigger button on main page.
     * @param view
     */
    public void launchBuilditBigger(View view) {
        Toast.makeText(this, "This button will launch BUILD IT BIGGER", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is responsible for starting new activity for XYZ
     * Reader application. This method will be called when user selects
     * XYZ Reader button on main page.
     * @param view
     */
    public void launchXyzReader(View view) {
        Toast.makeText(this, "This button will launch XYZ READER", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is responsible for starting new activity for My
     * Custom application. This method will be called when user selects
     * Capstone button on main page.
     * @param view
     */
    public void launchMyOwnApp(View view) {
        Toast.makeText(this, "This button will launch CAPSTONE: MY OWN APP", Toast.LENGTH_SHORT).show();
    }
}
