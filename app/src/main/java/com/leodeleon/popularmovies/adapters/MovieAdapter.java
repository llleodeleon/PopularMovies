package com.leodeleon.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leodeleon.popularmovies.R;
import com.leodeleon.popularmovies.model.Movie;
import com.leodeleon.popularmovies.model.MovieDetail;

import java.util.ArrayList;

/**
 * Created by leodeleon on 09/02/2017.
 */

public class MovieAdapter extends LoaderAdapter<Movie> {

  public MovieAdapter(Context context, ArrayList<Movie> movies) {
    super(context, movies);
  }

  @Override
  protected long getContentItemId(int position) {
    return content.get(position).getId();
  }

  @Override
  protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup) {
    return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_view, viewGroup, false));
  }

  @Override
  protected void bindView(RecyclerView.ViewHolder holder, int position) {
    ((MovieViewHolder) holder).bindView(content.get(position));
  }
}
