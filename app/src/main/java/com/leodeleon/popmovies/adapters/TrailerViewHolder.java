package com.leodeleon.popmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.crash.FirebaseCrash;
import com.leodeleon.popmovies.BuildConfig;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.ui.DetailsActivity;
import com.leodeleon.popmovies.util.GlideHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.leodeleon.popmovies.util.Constants.INITIALIZED;
import static com.leodeleon.popmovies.util.Constants.INITIALIZING;
import static com.leodeleon.popmovies.util.Constants.UNINITIALIZED;
import static com.leodeleon.popmovies.util.Constants.YOUTUBE_BASE_URL;


/**
 * Created by leodeleon on 10/02/2017.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  @BindView(R.id.thumbnail)
  YouTubeThumbnailView mThumbnailView;

  Context context;
  String videoId;
  String apiKey = BuildConfig.GOOGLE_APIS_KEY;

  public TrailerViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    itemView.setOnClickListener(this);
    this.context = itemView.getContext();
    initialize();
  }

  public void initialize() {
    mThumbnailView.setTag(R.id.initialize, INITIALIZING);
    mThumbnailView.setTag(R.id.thumbnailloader, null);
    mThumbnailView.setTag(R.id.videoid, "");


    mThumbnailView.initialize(apiKey, new YouTubeThumbnailView.OnInitializedListener() {
      @Override
      public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        mThumbnailView.setTag(R.id.initialize, INITIALIZED);
        mThumbnailView.setTag(R.id.thumbnailloader, youTubeThumbnailLoader);
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
          @Override
          public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String loadedVideoId) {
            youTubeThumbnailView.setVisibility(View.VISIBLE);
          }

          @Override
          public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
            FirebaseCrash.log(errorReason.toString());
          }
        });

        videoId = (String) mThumbnailView.getTag(R.id.videoid);
        youTubeThumbnailLoader.setVideo(videoId);
      }

      @Override
      public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
        mThumbnailView.setTag(R.id.initialize, UNINITIALIZED);
        FirebaseCrash.log(youTubeInitializationResult.toString());
      }
    });

  }

  public void bindView(final String videoId) {
    this.videoId = videoId;
    mThumbnailView.setTag(R.id.videoid, videoId);
    int state = (int) mThumbnailView.getTag(R.id.initialize);
    if (state == UNINITIALIZED) {
      initialize();
    }
    if (state == INITIALIZED) {
      YouTubeThumbnailLoader loader = (YouTubeThumbnailLoader) mThumbnailView.getTag(R.id.thumbnailloader);
      loader.setVideo(videoId);
    }
    GlideHelper.loadThumbnail(context, videoId, mThumbnailView);
  }

  @Override
  public void onClick(View v) {
    if(YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context).equals(YouTubeInitializationResult.SUCCESS)){
      ((DetailsActivity)context).startVideo(videoId);
    }else{
      CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
      builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
      builder.addDefaultShareMenuItem();
      CustomTabsIntent customTabsIntent = builder.build();
      customTabsIntent.launchUrl(context, Uri.parse(String.format(YOUTUBE_BASE_URL, videoId)));
    }

  }
}
