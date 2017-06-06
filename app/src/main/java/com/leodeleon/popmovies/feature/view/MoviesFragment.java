package com.leodeleon.popmovies.feature.view;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.di.Injectable;
import com.leodeleon.popmovies.feature.adapters.LoaderAdapter;
import com.leodeleon.popmovies.feature.adapters.MovieAdapter;
import com.leodeleon.popmovies.feature.viewModel.MoviesViewModel;
import com.leodeleon.popmovies.model.Movie;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by leodeleon on 11/02/2017.
 */

public class MoviesFragment extends LifecycleFragment implements Injectable {

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.progress_bar) ProgressBar mProgressBar;
  @BindView(R.id.no_favorites) TextView mPlaceholderText;


  @Inject public ViewModelProvider.Factory viewModelFactory;
  private static final String TAG = "MoviesFragment";

  private static final String POSITION = "position";
  private static final String LAYOUT_MANAGER_STATE = "state";
  public static final int POSITION_POPULAR = 0;
  public static final int POSITION_RATED = 1;
  public static final int POSITION_FAVORITE = 2;
  private MovieAdapter adapter;
  private Unbinder unbinder;
  private CompositeDisposable disposables = new CompositeDisposable();
  private MoviesViewModel viewModel;
  private GridLayoutManager layoutManager;

  private PublishProcessor<Integer> paginator = PublishProcessor.create();
  private int pageNumber = 1;
  private int lastVisibleItem, totalItemCount, visibleThreshold;
  private int position;
  private MovieData movieData;
  private PopMoviesData popMoviesData = new PopMoviesData();
  private TopMoviesData topMoviesData = new TopMoviesData();
  private FavMoviesData favMoviesData = new FavMoviesData();

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
    position = getArguments().getInt(POSITION);
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel.class);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setMovieData();
    setRecyclerView();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    movieData.observeLiveData();
    subscribe();
    if (savedInstanceState == null) {
      paginator.onNext(pageNumber);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    disposables.clear();
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    if (savedInstanceState != null) {
      Parcelable state = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
      layoutManager.onRestoreInstanceState(state);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManager.onSaveInstanceState());
    super.onSaveInstanceState(outState);
  }

  private void setMovieData() {
    if (position == POSITION_POPULAR) {
      movieData = popMoviesData;
    } else if (position == POSITION_RATED) {
      movieData = topMoviesData;
    } else if (position == POSITION_FAVORITE) {
      movieData = favMoviesData;
    }
  }

  private void setData(List<Movie> movieList) {
    adapter.addMovies(movieList);
    mProgressBar.setVisibility(View.GONE);
  }

  private void subscribe() {
    Disposable d1 = RxRecyclerView.scrollEvents(mRecyclerView).subscribe(recyclerViewScrollEvent -> {
      totalItemCount = layoutManager.getItemCount() - adapter.getFooterItemCount();
      lastVisibleItem = layoutManager.findLastVisibleItemPosition();
      visibleThreshold = layoutManager.getSpanCount();
      Timber.i("totalItemCount " + totalItemCount);
      Timber.i("lastVisibleItem " + lastVisibleItem);
      boolean shouldLoadMore = !adapter.isLoading() &&
              totalItemCount > 0 &&
              lastVisibleItem + visibleThreshold > totalItemCount &&
              position != POSITION_FAVORITE;

      if (shouldLoadMore) {
        pageNumber++;
        adapter.startLoading();
        paginator.onNext(pageNumber);
      }
    });
    movieData.subscribe();
    disposables.add(d1);
  }

  private void setRecyclerView() {
    adapter = new MovieAdapter();
    layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
    adapter.setHasStableIds(true);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.setAdapter(adapter);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
          return adapter.getItemViewType(position) == LoaderAdapter.VIEW_TYPE_FOOTER ? 2 : 1;
      }
    });
  }

  interface MovieData{
    void observeLiveData();
    void subscribe();
  }

  class PopMoviesData implements MovieData{

    @Override public void observeLiveData() {
      viewModel.getPopMoviesLiveData().observe(MoviesFragment.this, MoviesFragment.this::setData);
    }

    @Override public void subscribe() {
      final Disposable d2 = paginator.onBackpressureDrop().subscribe(page -> viewModel.loadPopularMovies(page));
      disposables.add(d2);
    }
  }

  class TopMoviesData implements MovieData{

    @Override public void observeLiveData() {
      viewModel.getTopMoviesLiveData().observe(MoviesFragment.this, MoviesFragment.this::setData);
    }

    @Override public void subscribe() {
      final Disposable d2 = paginator.onBackpressureDrop().subscribe(page -> viewModel.loadTopRatedMovies(page));
      disposables.add(d2);
    }
  }

  class FavMoviesData implements MovieData{

    @Override public void observeLiveData() {
      viewModel.getFavMoviesLiveData().observe(MoviesFragment.this, movies1 -> {
        adapter.setMovies(movies1);
        if (movies1 == null || movies1.size() == 0) {
          mPlaceholderText.setVisibility(View.VISIBLE);
        } else {
          mPlaceholderText.setVisibility(View.GONE);
        }
        mProgressBar.setVisibility(View.GONE);
      });
    }

    @Override public void subscribe() {
      Disposable d2 = paginator.onBackpressureDrop().subscribe(page -> viewModel.loadFavoriteMovies());
      disposables.add(d2);
    }
  }

}
