package com.leodeleon.popmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.ui.DetailActivity;
import com.leodeleon.popmovies.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leodeleon on 09/02/2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
  Movie movie;
  @BindView(R.id.movie_title)
  TextView mTitleText;
  @BindView(R.id.movie_year)
  TextView mYearText;
  @BindView(R.id.cardview)
  CardView mCardView;
  Context context;

  public MovieViewHolder(View view) {
    super(view);
    ButterKnife.bind(this, view);
    mCardView.setOnClickListener(this);
    context = itemView.getContext();
  }

  public void bindView(Movie movie) {
    this.movie = movie;
    mTitleText.setText(movie.getTitle());
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(context, DetailActivity.class);
    context.startActivity(intent);
  }
}
