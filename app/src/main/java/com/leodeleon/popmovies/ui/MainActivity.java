package com.leodeleon.popmovies.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.adapters.LoaderAdapter;
import com.leodeleon.popmovies.adapters.MovieAdapter;
import com.leodeleon.popmovies.api.MovieCalls;
import com.leodeleon.popmovies.interfaces.MoviesResultCallback;
import com.leodeleon.popmovies.listeners.PaginationScrollListener;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.model.MoviesResult;
import com.leodeleon.popmovies.util.Constants;
import com.leodeleon.popmovies.util.SharedPreferencesUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;
  @BindView(R.id.progress_bar)
  ProgressBar mProgressBar;
  @BindView(R.id.toolbar)
  public Toolbar mToolbar;

  private MoviesResult popularMoviesResult;
  private MoviesResult topRatedMoviesResult;
  private MovieAdapter popularMoviesAdapter;
  private MovieAdapter topRatedMoviesAdapter;
  private ArrayList<Movie> popularMovies = new ArrayList<>();
  private ArrayList<Movie> topRatedMovies = new ArrayList<>();
  private boolean sortByPopular;
  private boolean sortByTopRated;
  private int popularPage = 1;
  private int ratedPage = 1;
  private Unbinder unbinder;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(mToolbar);

    sortByPopular = SharedPreferencesUtil.getInstance().getBoolean(Constants.SORT_BY_POPULARITY);
    sortByTopRated = SharedPreferencesUtil.getInstance().getBoolean(Constants.SORT_BY_TOP_RATED);
    getMovies();

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    MenuItem item1 = menu.findItem(R.id.sort_popular);
    item1.setChecked(sortByPopular);
    MenuItem item2 = menu.findItem(R.id.sort_rated);
    item2.setChecked(sortByTopRated);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    sortByPopular = !sortByPopular;
    sortByTopRated = !sortByTopRated;

    mRecyclerView.swapAdapter(sortByPopular ? popularMoviesAdapter : topRatedMoviesAdapter, false);
    SharedPreferencesUtil.getInstance().putBoolean(Constants.SORT_BY_POPULARITY, sortByPopular);
    SharedPreferencesUtil.getInstance().putBoolean(Constants.SORT_BY_TOP_RATED, sortByTopRated);

    switch (item.getItemId()) {
      case R.id.sort_popular:
        item.setChecked(sortByPopular);
        return true;
      case R.id.sort_rated:
        item.setChecked(sortByTopRated);
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void getMovies() {


    MovieCalls.getInstance().getTopRatedMovies(ratedPage, new MoviesResultCallback() {
      @Override
      public void callback(MoviesResult moviesResult) {
        mProgressBar.setVisibility(View.GONE);
        topRatedMoviesResult = moviesResult;
        topRatedMovies = topRatedMoviesResult.getMovies();
        topRatedMoviesAdapter = new MovieAdapter(MainActivity.this, topRatedMovies);
        topRatedMoviesAdapter.setHasStableIds(true);
      }
    });

    MovieCalls.getInstance().getPopularMovies(popularPage, new MoviesResultCallback() {
      @Override
      public void callback(MoviesResult moviesResult) {
        popularMoviesResult = moviesResult;
        popularMovies = popularMoviesResult.getMovies();
        popularMoviesAdapter = new MovieAdapter(MainActivity.this, popularMovies);
        popularMoviesAdapter.setHasStableIds(true);
        setRecyclerView();
      }
    });

    setRecyclerView();

  }

  private void setRecyclerView() {
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.setAdapter(popularMoviesAdapter);
    mRecyclerView.addOnScrollListener(new PaginationScrollListener(mRecyclerView) {
      boolean sortByPopular = SharedPreferencesUtil.getInstance().getBoolean(Constants.SORT_BY_POPULARITY);
      @Override
      protected void loadMoreItems() {
        if (sortByPopular) {
          popularPage += 1;
          popularMoviesAdapter.startLoading();
          MovieCalls.getInstance().getPopularMovies(popularPage, new MoviesResultCallback() {
            @Override
            public void callback(MoviesResult moviesResult) {
              popularMovies.addAll(moviesResult.getMovies());
              popularMoviesAdapter.stopLoading();
              popularMoviesAdapter.notifyDataSetChanged();
            }
          });
        } else {
          ratedPage += 1;
          topRatedMoviesAdapter.startLoading();
          MovieCalls.getInstance().getPopularMovies(ratedPage, new MoviesResultCallback() {
            @Override
            public void callback(MoviesResult moviesResult) {
              topRatedMovies.addAll(moviesResult.getMovies());
              topRatedMoviesAdapter.stopLoading();
              topRatedMoviesAdapter.notifyDataSetChanged();
            }
          });
        }
      }

      @Override
      public boolean isLastPage() {
        if (sortByPopular) {
          return popularPage == popularMoviesResult.getTotalPages();
        } else {
          return ratedPage == topRatedMoviesResult.getTotalPages();
        }
      }
    });
  }
}
