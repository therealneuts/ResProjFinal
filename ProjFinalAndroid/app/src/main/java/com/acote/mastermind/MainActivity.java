package com.acote.mastermind;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import android.view.MenuItem;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MastermindGame.OnMastermindInteractionListener {

    private ScoreViewModel mScoreViewModel;

    private ScoreListFragment mScoreList;

    public static final int SCORE_ADD_CODE = 1;

    public static final String BUNDLE_SCORE = "score";

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mScoreViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);

        mScoreList = ScoreListFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_frameLayout, mScoreList)
                                    .addToBackStack(null)
                                    .commit();
    }

    private void newGame() {
        MastermindGame game = MastermindGame.newInstance(10, false, false, "10.4.131.9", 42069);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frameLayout, game)
                .commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentInteraction(420);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frameLayout, mScoreList)
                    .commit();

        } else if (id == R.id.nav_gallery) {
            MastermindGame game = MastermindGame.newInstance(10, false, false, "10.4.131.9", 42069);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frameLayout, game)
                    .commit();


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String playerName;

    @Override
    public void onFragmentInteraction(int score) {

        Intent scoreAddActivity = new Intent(this, ScoreAddActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(BUNDLE_SCORE, score);

        scoreAddActivity.putExtras(extras);

        startActivityForResult(scoreAddActivity, SCORE_ADD_CODE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frameLayout, mScoreList)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("MAIN", String.valueOf(requestCode));
        Log.i("MAIN", String.valueOf(resultCode));

        if (requestCode == SCORE_ADD_CODE){
            if (resultCode == RESULT_CANCELED){
                Log.i("MAIN", "???");
            }
            else if (resultCode == RESULT_OK){
                Bundle info = data.getExtras();
                int score = info.getInt(BUNDLE_SCORE);
                String name = info.getString(ScoreAddActivity.BUNDLE_NAME);

                Log.i("MAIN", name + String.valueOf(score));

                Score newInsert = new Score(name, score);

                mScoreViewModel.insert(newInsert);


            }
        }
    }
}
