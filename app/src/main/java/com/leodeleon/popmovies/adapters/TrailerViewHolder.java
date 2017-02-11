package com.leodeleon.popmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.leodeleon.popmovies.BuildConfig;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.ui.DetailsActivity;
import com.leodeleon.popmovies.util.GlideHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.leodeleon.popmovies.util.Constants.YOUTUBE_BASE_URL;


/**
 * Created by leodeleon on 10/02/2017.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  @BindView(R.id.thumbnail)
  ImageView mThumbnailView;

  Context context;
  String videoId;
  String apiKey = BuildConfig.GOOGLE_APIS_KEY;

  public TrailerViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    itemView.setOnClickListener(this);
    this.context = itemView.getContext();
  }

  public void bindView(final String videoId) {
    this.videoId = videoId;
    GlideHelper.loadThumbnail(context, videoId, mThumbnailView);
  }

  @Override
  public void onClick(View v) {
    if(YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context).equals(YouTubeInitializationResult.SUCCESS)){
      context.startActivity(
        YouTubeStandalonePlayer.createVideoIntent(
          (DetailsActivity) context,
          BuildConfig.GOOGLE_APIS_KEY,
          videoId, 0, false, true)
      );
    }else{
      CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
      builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
      builder.addDefaultShareMenuItem();
      CustomTabsIntent customTabsIntent = builder.build();
      customTabsIntent.launchUrl(context, Uri.parse(String.format(YOUTUBE_BASE_URL, videoId)));
    }
  }
}