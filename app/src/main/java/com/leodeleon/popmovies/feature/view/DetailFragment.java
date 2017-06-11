package com.leodeleon.popmovies.feature.view;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindDimen;
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
import com.leodeleon.popmovies.model.Genre;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.util.GlideHelper;
import com.robertlevonyan.views.chip.Chip;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import java.util.Locale;
import javax.inject.Inject;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;
import org.apmem.tools.layouts.FlowLayout;

public class DetailFragment extends LifecycleFragment implements Injectable {
  @BindView(R.id.scroll_view) NestedScrollView mScrollView;
  @BindView(R.id.backdrop) ImageView mBackdropView;
  @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbar;
  @BindView(R.id.fab) FloatingActionButton mFloatingButton;
  @BindView(R.id.poster) ImageView mPosterView;
  @BindView(R.id.vote_average) TextView mVoteAvgText;
  @BindView(R.id.release_date) TextView mYearText;
  @BindView(R.id.text_trailers) TextView mVideosText;
  @BindView(R.id.runtime) TextView mRuntimeText;
  @BindView(R.id.overview) TextView mOverviewText;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.flow_layout) FlowLayout mFlowLayout;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindDrawable(R.drawable.toolbar_gradient) Drawable gradientToolbar;
  @BindDrawable(R.drawable.bg_gradient) Drawable gradientBackground;
  @BindString(R.string.movie_added) String added;
  @BindString(R.string.movie_removed) String removed;
  @BindColor(R.color.colorPrimaryDark) int red;
@BindDimen(R.dimen.dp8) int margin;
  String runtime = "%dmin";
  String voting ="%1$.1f/10";
  Unbinder unbinder;
  TrailerAdapter adapter;
  MovieDetailsViewModel viewModel;
  @Inject ViewModelProvider.Factory viewModelFactory;
  @Inject PublishSubject<Movie> movieSubject;
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
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    bindMovie();
    setRecyclerView();
    setToolbar();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel.class);
    if (savedInstanceState == null) {
      viewModel.loadDetails(movie.getId());
    }
    observeLiveData();
    subscribe();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    disposable.clear();
  }

  private void setToolbar() {
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

  private void subscribe() {
    Disposable d1 = RxView.clicks(mFloatingButton).subscribe(o ->{
      boolean isSelected = !mFloatingButton.isSelected();
      mFloatingButton.setSelected(isSelected);
      if (isSelected) {
        viewModel.addFavorite(movie);
      } else {
        viewModel.removeFavorite(movie);
      }
    });

    Disposable d2 = movieSubject.subscribe(movie1 -> {
      Snackbar snackbar = Snackbar.make(getView(), movie1.isFavorite()? added : removed, Snackbar.LENGTH_SHORT);
      snackbar.getView().setBackgroundColor(red);
      snackbar.show();
    } );

    disposable.add(d1);
    disposable.add(d2);
  }

  private void setRecyclerView() {
    adapter = new TrailerAdapter();
    mRecyclerView.setAdapter(adapter);
  }

  private void bindMovie() {
    GlideHelper.Companion.loadBackdrop(getContext(), movie.getBackdropPath(), mBackdropView);
    GlideHelper.Companion.loadPoster(getContext(), movie.getPosterPath(), mPosterView);
    mCollapsingToolbar.setTitle(movie.getTitle());
    mVoteAvgText.setText(String.format(Locale.getDefault(), voting, movie.getVoteAverage()));
    mYearText.setText(movie.getReleaseDate());
    mOverviewText.setText(movie.getOverview());
    mFloatingButton.setSelected(movie.isFavorite());
  }

  private void bindDetails() {
    mRuntimeText.setText(String.format(Locale.getDefault(), runtime, movieDetail.getRuntime()));
    mFlowLayout.removeAllViews();
    for (Genre genre: movieDetail.getGenres()) {
      Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.view_chip, null);
      FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
          FlowLayout.LayoutParams.WRAP_CONTENT,
          FlowLayout.LayoutParams.WRAP_CONTENT
      );
      params.setMargins(0,0,margin,margin);
      chip.setLayoutParams(params);
      chip.setChipText(genre.getName());
      mFlowLayout.addView(chip);

    }
  }
}
