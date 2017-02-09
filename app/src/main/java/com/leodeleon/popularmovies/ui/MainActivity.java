package com.leodeleon.popularmovies.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.leodeleon.popularmovies.R;
import com.leodeleon.popularmovies.adapters.MovieAdapter;
import com.leodeleon.popularmovies.api.MovieCalls;
import com.leodeleon.popularmovies.interfaces.MovieResultsCallback;
import com.leodeleon.popularmovies.model.MovieResults;
import com.leodeleon.popularmovies.util.Constants;
import com.leodeleon.popularmovies.util.SharedPreferencesUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  private RecyclerView mRecyclerView;
  private MovieAdapter adapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    findViews();

//    mCardView.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//        ActivityOptionsCompat options = ActivityOptionsCompat.
//          makeSceneTransitionAnimation(MainActivity.this, mCardView, "toolbar");
//        startActivity(intent, options.toBundle());
//      }
//    });
//
//
//    MovieCalls.getInstance().getTopRatedMovies(new MovieResultsCallback() {
//      @Override
//      public void callback(MovieResults movies) {
//        mTextView.setText(movies.getResults().get(0).getTitle());
//
//      }
//    });

  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    MenuItem item1 = menu.findItem(R.id.sort_popular);
    item1.setChecked(SharedPreferencesUtil.getInstance().getBoolean(Constants.SORT_BY_POPULARITY));
    MenuItem item2 = menu.findItem(R.id.sort_popular);
    item2.setChecked(SharedPreferencesUtil.getInstance().getBoolean(Constants.SORT_BY_POPULARITY));
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    item.setChecked(!item.isChecked());
    switch (item.getItemId()) {
      case R.id.sort_popular:
        SharedPreferencesUtil.getInstance().putBoolean(Constants.SORT_BY_POPULARITY, item.isChecked());
        break;
      case R.id.sort_rated:
        SharedPreferencesUtil.getInstance().putBoolean(Constants.SORT_BY_TOP_RATED, item.isChecked());
      break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void findViews() {
//    mTextView = (TextView) findViewById(R.id.textview);
//    mFab = (FloatingActionButton) findViewById(R.id.button);
//    mCardView = (CardView) findViewById(R.id.cardview);
      mRecyclerView = (RecyclerView) findViewById(R.id.cardview);
  }

  private void setRecyclerView() {

  }
}
