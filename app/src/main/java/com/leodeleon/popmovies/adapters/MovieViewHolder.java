package com.leodeleon.popmovies.adapters;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.feature.DetailsActivity;
import com.leodeleon.popmovies.feature.MainActivity;
import com.leodeleon.popmovies.util.GlideHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leodeleon on 09/02/2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
  Movie movie;
  @BindView(R.id.cardview)
  CardView mCardView;
  @BindView(R.id.poster)
  ImageView mPosterImage;
  MainActivity activity;

  public MovieViewHolder(View view) {
    super(view);
    ButterKnife.bind(this, view);
    mCardView.setOnClickListener(this);
    activity = (MainActivity) itemView.getContext();
  }

  public void bindView(Movie movie) {
    this.movie = movie;
    GlideHelper.loadPoster(activity, movie.getPosterPath(), mPosterImage);
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(activity, DetailsActivity.class);
    intent.putExtra(DetailsActivity.MOVIE, new Gson().toJson(movie));
    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, mCardView, "cardview");
    activity.startActivity(intent, options.toBundle());
  }
}
