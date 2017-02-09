package com.leodeleon.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.leodeleon.popularmovies.R;

/**
 * Created by leodeleon on 09/02/2017.
 */

public class LoaderViewHolder extends RecyclerView.ViewHolder {
  private ProgressBar progressBar;

  public LoaderViewHolder(View view) {
    super(view);
    progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
  }
}