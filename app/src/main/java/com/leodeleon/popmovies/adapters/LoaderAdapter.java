package com.leodeleon.popmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leodeleon.popmovies.R;

import java.util.ArrayList;

/**
 * Created by leodeleon on 09/02/2017.
 */

public abstract class LoaderAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public static final int NORMAL_ITEM_VIEW = 1;
  public static final int LOADER_VIEW = 2;
  private boolean isLoading = false;
  protected ArrayList<T> content = new ArrayList<>();
  protected Context context;

  public LoaderAdapter(Context context, ArrayList<T> content) {
    this.context = context;
    this.content = content;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

    if (viewType == LOADER_VIEW) {
      return getLoadingView(viewGroup);
    } else if (viewType == NORMAL_ITEM_VIEW) {
      return getViewHolder(viewGroup);
    }
    throw new IllegalArgumentException("Invalid ViewType: " + viewType);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof LoaderViewHolder) {
      ((LoaderViewHolder) holder).itemView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
      return;
    }
    bindView(holder, position);
  }


  @Override
  public int getItemViewType(int position) {
    if (position != 0 && position == getItemCount() - 1) {
      return LOADER_VIEW;
    }
    return NORMAL_ITEM_VIEW;
  }

  @Override
  public int getItemCount() {
    if (content == null || content.size() == 0) {
      return 0;
    }
    return content.size() + 1;
  }

  @Override
  public long getItemId(int position) {
    if (position != 0 && position == getItemCount() - 1) {
      return -1;
    }
    return getContentItemId(position);
  }


  public void startLoading() {
    isLoading = true;
  }

  public void stopLoading() {
    isLoading = false;
  }

  public RecyclerView.ViewHolder getLoadingView(ViewGroup view) {
    return new LoaderViewHolder(LayoutInflater.from(context).inflate(R.layout.loader_view, view, false));
  }

  protected abstract long getContentItemId(int position);

  protected abstract RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup);

  protected abstract void bindView(RecyclerView.ViewHolder holder, int position);

}
