package com.leodeleon.popmovies.feature;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.adapters.LoaderAdapter;
import com.leodeleon.popmovies.adapters.MovieAdapter;
import com.leodeleon.popmovies.di.Injectable;
import com.leodeleon.popmovies.model.Movie;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by leodeleon on 11/02/2017.
 */

public class MoviesFragment extends LifecycleFragment implements Injectable {

  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;
  @BindView(R.id.progress_bar)
  ProgressBar mProgressBar;

  @Inject ViewModelProvider.Factory viewModelFactory;
  private static final String TAG = "MoviesFragment";

  public static final String POSITION = "position";
  private static final String LAYOUT_MANAGER_STATE = "state";
  public static final int POSITION_POPULAR = 0;
  public static final int POSITION_RATED = 1;
  public static final int POSITION_FAVORITE = 2;
  private MovieAdapter adapter;
  private List<Movie> movies = new ArrayList<>();
  private Unbinder unbinder;
  CompositeDisposable disposables = new CompositeDisposable();
  private MoviesViewModel viewModel;
  private GridLayoutManager layoutManager;

  private PublishProcessor<Integer> paginator = PublishProcessor.create();
  private int pageNumber = 1;
  private int lastVisibleItem, totalItemCount;
  private final int VISIBLE_THRESHOLD = 1;

  int position;

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
    this.position = getArguments().getInt(POSITION, 0);

    setRecyclerView();
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel.class);
    observeLiveData();
    subscribe();
    //paginator.onNext(pageNumber);
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

  private void observeLiveData() {
    viewModel.getMoviesLiveData().observe(this, movies1 -> {
      adapter.setMovies(movies1);
      mProgressBar.setVisibility(View.GONE);
    });
  }

  private void subscribe() {
    Disposable d1 = RxRecyclerView.scrollEvents(mRecyclerView).subscribe(recyclerViewScrollEvent -> {
      totalItemCount = layoutManager.getItemCount() - adapter.getFooterItemCount();
      lastVisibleItem = layoutManager.findLastVisibleItemPosition();
      if (!adapter.isLoading() && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
        Log.i(TAG, "subscribe: " + totalItemCount + "  " + lastVisibleItem + VISIBLE_THRESHOLD );
        pageNumber++;
        adapter.startLoading();
        paginator.onNext(pageNumber);
      }
    });

    Disposable d2 = paginator.onBackpressureDrop().subscribe(page -> {
      if (position == POSITION_POPULAR) {
        viewModel.loadPopularMovies(page);
      } else if (position == POSITION_RATED) {
        viewModel.loadTopRatedMovies(page);
      } else if (position == POSITION_FAVORITE) {
        viewModel.loadFavoriteMovies();
      }
    });

    disposables.add(d1);
    disposables.add(d2);
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

}
