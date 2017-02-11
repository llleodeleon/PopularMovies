package com.leodeleon.popmovies.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.adapters.LoaderAdapter;
import com.leodeleon.popmovies.adapters.MovieAdapter;
import com.leodeleon.popmovies.api.MovieCalls;
import com.leodeleon.popmovies.interfaces.MoviesResultCallback;
import com.leodeleon.popmovies.listeners.PaginationScrollListener;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.model.MoviesResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by leodeleon on 11/02/2017.
 */

public class MoviesFragment extends Fragment {

  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;
  @BindView(R.id.progress_bar)
  ProgressBar mProgressBar;

  public static final String POSITION = "position";
  private static final String LAYOUT_MANAGER_STATE = "state";
  public static final int POSITION_POPULAR = 0;
  public static final int POSITION_RATED = 1;
  private MoviesResult popularMoviesResult;
  private MoviesResult topRatedMoviesResult;
  private MovieAdapter adapter;
  private ArrayList<Movie> popularMovies = new ArrayList<>();
  private ArrayList<Movie> topRatedMovies = new ArrayList<>();
  private int popularPages = 1;
  private int ratedPages = 1;
  private Unbinder unbinder;


  int position;
  Context context;

  public static MoviesFragment newInstance(int position) {
    MoviesFragment fragment = new MoviesFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(POSITION, position);
    fragment.setArguments(bundle);
    return fragment;
  }



  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_movies, container, false);
    unbinder = ButterKnife.bind(this, view);
    this.context = getContext();
    this.position = getArguments().getInt(POSITION, 0);
    getMovies();
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    if (savedInstanceState != null) {
      Parcelable state = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
      mRecyclerView.getLayoutManager().onRestoreInstanceState(state);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putParcelable(LAYOUT_MANAGER_STATE, mRecyclerView.getLayoutManager().onSaveInstanceState());
    super.onSaveInstanceState(outState);
  }

  private void getMovies() {
    if (position == POSITION_POPULAR) {
      MovieCalls.getInstance().getPopularMovies(popularPages, new MoviesResultCallback() {
        @Override
        public void callback(MoviesResult moviesResult) {
          popularMoviesResult = moviesResult;
          popularMovies = popularMoviesResult.getMovies();
          setRecyclerView(popularMovies);
          mProgressBar.setVisibility(View.GONE);
        }
      });
    } else {
      MovieCalls.getInstance().getTopRatedMovies(ratedPages, new MoviesResultCallback() {
        @Override
        public void callback(MoviesResult moviesResult) {
          mProgressBar.setVisibility(View.GONE);
          topRatedMoviesResult = moviesResult;
          topRatedMovies = topRatedMoviesResult.getMovies();
          setRecyclerView(topRatedMovies);
          mProgressBar.setVisibility(View.GONE);
        }
      });
    }
  }

  private void setRecyclerView(ArrayList<Movie> movies) {
    final MovieAdapter adapter = new MovieAdapter(context, movies);
    adapter.setHasStableIds(true);
    GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
          return adapter.getItemViewType(position) == LoaderAdapter.LOADER_VIEW? 2 :1;
      }
    });

    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.setAdapter(adapter);
    mRecyclerView.addOnScrollListener(new PaginationScrollListener() {
      @Override
      protected void loadMoreItems() {
        if (position == POSITION_POPULAR) {
          popularPages += 1;
          adapter.startLoading();
          MovieCalls.getInstance().getPopularMovies(popularPages, new MoviesResultCallback() {
            @Override
            public void callback(MoviesResult moviesResult) {
              popularMovies.addAll(moviesResult.getMovies());
              adapter.stopLoading();
              adapter.notifyDataSetChanged();
            }
          });
        } else {
          ratedPages += 1;
          adapter.startLoading();
          MovieCalls.getInstance().getTopRatedMovies(ratedPages, new MoviesResultCallback() {
            @Override
            public void callback(MoviesResult moviesResult) {
              topRatedMovies.addAll(moviesResult.getMovies());
              adapter.stopLoading();
              adapter.notifyDataSetChanged();
            }
          });
        }
      }
    });
  }
}
