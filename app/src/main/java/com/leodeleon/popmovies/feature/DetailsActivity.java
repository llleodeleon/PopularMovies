package com.leodeleon.popmovies.feature;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.adapters.TrailerAdapter;
import com.leodeleon.popmovies.api.MovieCalls;
import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.util.GlideHelper;
import java.util.ArrayList;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

public class DetailsActivity extends AppCompatActivity {

  public final static String MOVIE = "movieDetail";

  @BindView(R.id.scroll_view)
  NestedScrollView mScrollView;
  @BindView(R.id.backdrop)
  ImageView mBackdropView;
  @BindView(R.id.collapsing_toolbar)
  CollapsingToolbarLayout mCollapsingToolbar;
  @BindView(R.id.fab)
  FloatingActionButton mFloatingButton;
  @BindView(R.id.poster)
  ImageView mPosterView;
  @BindView(R.id.vote_average)
  TextView mVoteAvgText;
  @BindView(R.id.release_date)
  TextView mYearText;
  @BindView(R.id.videos)
  TextView mVideosText;
  @BindView(R.id.runtime)
  TextView mRuntimeText;
  @BindView(R.id.overview)
  TextView mOverviewText;
  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindString(R.string.runtime_label)
  String runtime;
  @BindString(R.string.voting_label)
  String voting;
  @BindDrawable(R.drawable.toolbar_gradient)
  Drawable gradientToolbar;
  @BindDrawable(R.drawable.bg_gradient)
  Drawable gradientBackground;

  MovieDetail movieDetail;
  ArrayList<String> videoIds;

  Unbinder unbinder;
  TrailerAdapter trailerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    unbinder = ButterKnife.bind(this);
    mBackdropView.setImageDrawable(gradientBackground);
    setSupportActionBar(mToolbar);
    mToolbar.setBackground(gradientToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    movieDetail = getIntent().getParcelableExtra(MOVIE);
    bindViews();
    getDetails();
    getVideos();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        supportFinishAfterTransition();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setRecyclerView() {
    if (videoIds.size() > 0) {
      trailerAdapter = new TrailerAdapter(this, videoIds);
      mRecyclerView.setAdapter(trailerAdapter);
    } else {
      mRecyclerView.setVisibility(View.GONE);
      mVideosText.setVisibility(View.GONE);
    }
  }

  private void getDetails() {
    MovieCalls.getInstance().getMovieDetail(movieDetail.getId(), movie1 -> {
      movieDetail = movie1;
      mRuntimeText.setText(String.format(runtime, movie1.getRuntime()));
    });
  }

  private void getVideos() {
    MovieCalls.getInstance().getVideos(movieDetail.getId(), videos -> {
      videoIds = videos;
      setRecyclerView();
    });
  }

  private void bindViews() {
    GlideHelper.loadBackdrop(this, movieDetail.getBackdropPath(), mBackdropView);
    GlideHelper.loadPoster(this, movieDetail.getPosterPath(), mPosterView);
    mCollapsingToolbar.setTitle(movieDetail.getTitle());
    mVoteAvgText.setText(String.format(voting, movieDetail.getVoteAverage()));
    mYearText.setText(movieDetail.getReleaseDate());
    mOverviewText.setText(movieDetail.getOverview());
  }

  @OnClick({R.id.fab})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.fab:
        mFloatingButton.setSelected(!mFloatingButton.isSelected());
        break;
    }
  }
}
