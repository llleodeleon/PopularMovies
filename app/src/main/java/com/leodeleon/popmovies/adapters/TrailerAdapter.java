package com.leodeleon.popmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.leodeleon.popmovies.R;
import java.util.ArrayList;

/**
 * Created by leodeleon on 10/02/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

  private Context context;
  private ArrayList<String> videoKeys = new ArrayList<>();

  public TrailerAdapter(Context context, ArrayList<String> videoKeys) {
    this.context = context;
    this.videoKeys = videoKeys;
  }

  @Override
  public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TrailerViewHolder(LayoutInflater.from(context).inflate(R.layout.trailer_view, parent, false));
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((TrailerViewHolder)holder).bindView(videoKeys.get(position));
  }

  @Override
  public int getItemCount() {
    return videoKeys.size();
  }
}
