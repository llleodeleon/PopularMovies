package com.leodeleon.popmovies.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import static com.leodeleon.popmovies.util.Constants.MOVIES_IMG_URL;
import static com.leodeleon.popmovies.util.Constants.YOUTUBE_IMG_URL;

/**
 * Created by leodeleon on 10/02/2017.
 */

public class GlideHelper {

  public static void loadThumbnail(Context context, String videoId, ImageView imageView) {
    Glide.with(context)
        .load(String.format(YOUTUBE_IMG_URL, videoId))
        .centerCrop()
        .into(imageView);
  }

  public static void loadPoster(Context context, String posterPath, ImageView imageView) {
    Glide.with(context)
        .load(String.format(MOVIES_IMG_URL, "w342", posterPath))
        .centerCrop()
        .dontAnimate()
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
        .into(imageView);
  }

  public static void loadBackdrop(Context context, String backdropPath, final ImageView imageView) {
    Glide.with(context)
        .load(String.format(MOVIES_IMG_URL, "w780", backdropPath))
        .centerCrop()
        .into(new SimpleTarget<GlideDrawable>() {
      @Override
      public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
        imageView.setBackground(resource);
      }
    });
  }

}
