package com.leodeleon.popularmovies.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leodeleon.popularmovies.R;
import com.leodeleon.popularmovies.model.Movie;

/**
 * Created by leodeleon on 09/02/2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
  Movie movie;
  TextView mTitleText;
  TextView mYearText;
  CardView mCardView;

  public MovieViewHolder(View view) {
    super(view);
    view.setOnClickListener(this);
    mTitleText = (TextView) view.findViewById(R.id.movie_title);
    mYearText = (TextView) view.findViewById(R.id.movie_year);
    mCardView = (CardView) view.findViewById(R.id.cardview);
  }

  public void bindView(Movie movie) {
    this.movie = movie;
    mTitleText.setText(movie.getTitle());
  }

  @Override
  public void onClick(View v) {

  }
}
