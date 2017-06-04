package com.leodeleon.popmovies.feature.view;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.jakewharton.rxbinding2.view.RxView;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.di.Injectable;
import com.leodeleon.popmovies.feature.MainActivity;
import com.leodeleon.popmovies.feature.adapters.TrailerAdapter;
import com.leodeleon.popmovies.feature.viewModel.MovieDetailsViewModel;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.util.GlideHelper;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

public class DetailFragment extends LifecycleFragment implements Injectable {
  @BindView(R.id.scroll_view) NestedScrollView mScrollView;
  @BindView(R.id.backdrop) ImageView mBackdropView;
  @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbar;
  @BindView(R.id.fab) FloatingActionButton mFloatingButton;
  @BindView(R.id.poster) ImageView mPosterView;
  @BindView(R.id.vote_average) TextView mVoteAvgText;
  @BindView(R.id.release_date) TextView mYearText;
  @BindView(R.id.videos) TextView mVideosText;
  @BindView(R.id.runtime) TextView mRuntimeText;
  @BindView(R.id.overview) TextView mOverviewText;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindString(R.string.runtime_label) String runtime;
  @BindString(R.string.voting_label) String voting;
  @BindDrawable(R.drawable.toolbar_gradient) Drawable gradientToolbar;
  @BindDrawable(R.drawable.bg_gradient) Drawable gradientBackground;
  Unbinder unbinder;
  TrailerAdapter adapter;
  MovieDetailsViewModel viewModel;
  @Inject ViewModelProvider.Factory viewModelFactory;
  Movie movie;
  MovieDetail movieDetail;
  CompositeDisposable disposable = new CompositeDisposable();
  public static final String MOVIE = "MOVIE";

  public static DetailFragment newInstance(Movie movie) {
    Bundle args = new Bundle();
    DetailFragment fragment = new DetailFragment();
    args.putParcelable(MOVIE, movie);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail, container, false);
    unbinder = ButterKnife.bind(this, view);
    movie = getArguments().getParcelable(MOVIE);
    bindMovie();
    setRecyclerView();
    setToolbar();
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel.class);
    viewModel.loadDetails(movie.getId());
    observeLiveData();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    disposable.clear();
  }

  private void setToolbar() {
    mBackdropView.setImageDrawable(gradientBackground);
    ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
    mToolbar.setBackground(gradientToolbar);
    ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void observeLiveData() {
    viewModel.getVideoLiveData().observe(this, videoIds -> {
      if (videoIds != null && videoIds.size() > 0) {
        adapter.setVideoKeys(videoIds);
      } else {
        mRecyclerView.setVisibility(View.GONE);
        mVideosText.setVisibility(View.GONE);
      }
    });

    viewModel.getDetailLiveData().observe(this, movieDetail1 -> {
      movieDetail = movieDetail1;
      bindDetails();
    });
  }

  private void setRecyclerView() {
    adapter = new TrailerAdapter();
    mRecyclerView.setAdapter(adapter);
  }

  private void bindMovie() {
    GlideHelper.loadBackdrop(getContext(), movie.getBackdropPath(), mBackdropView);
    GlideHelper.loadPoster(getContext(), movie.getPosterPath(), mPosterView);
    mCollapsingToolbar.setTitle(movie.getTitle());
    mVoteAvgText.setText(String.format(voting, movie.getVoteAverage()));
    mYearText.setText(movie.getReleaseDate());
    mOverviewText.setText(movie.getOverview());
    Disposable d1 = RxView.clicks(mFloatingButton).subscribe(o -> mFloatingButton.setSelected(!mFloatingButton.isSelected()));
    disposable.add(d1);
  }

  private void bindDetails() {
    mRuntimeText.setText(String.format(runtime, movieDetail.getRuntime()));
  }
}
