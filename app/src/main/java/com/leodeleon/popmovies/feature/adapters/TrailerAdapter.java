package com.leodeleon.popmovies.feature.adapters;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.leodeleon.popmovies.BuildConfig;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.feature.MainActivity;
import com.leodeleon.popmovies.util.GlideHelper;
import java.util.ArrayList;
import java.util.List;

import static com.leodeleon.popmovies.util.Constants.YOUTUBE_BASE_URL;

/**
 * Created by leodeleon on 10/02/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

  private List<String> videoKeys = new ArrayList<>();

  public void setVideoKeys(List<String> videoKeys) {
    this.videoKeys = videoKeys;
    notifyDataSetChanged();
  }

  @Override
  public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TrailerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_trailer, parent, false));
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((TrailerViewHolder)holder).bindView(videoKeys.get(position));
  }

  @Override
  public int getItemCount() {
    return videoKeys.size();
  }

  class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.thumbnail) ImageView mThumbnailView;

    String videoId;

    public TrailerViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    public void bindView(final String videoId) {
      this.videoId = videoId;
      GlideHelper.loadThumbnail(itemView.getContext(), videoId, mThumbnailView);
    }

    @Override
    public void onClick(View v) {
      MainActivity activity = (MainActivity) itemView.getContext();
      if(YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(activity).equals(YouTubeInitializationResult.SUCCESS)){
        activity.startActivity(
            YouTubeStandalonePlayer.createVideoIntent(activity, BuildConfig.GOOGLE_APIS_KEY, videoId, 0, false, true)
        );
      }else{
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        builder.addDefaultShareMenuItem();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(String.format(YOUTUBE_BASE_URL, videoId)));
      }
    }
  }
}
