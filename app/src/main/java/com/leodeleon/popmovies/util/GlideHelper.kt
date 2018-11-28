package com.leodeleon.popmovies.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.leodeleon.popmovies.util.Constants.MOVIES_IMG_URL
import com.leodeleon.popmovies.util.Constants.YOUTUBE_IMG_URL

/**
 * Created by leodeleon on 10/02/2017.
 */

class GlideHelper {
  companion object {
    fun loadThumbnail(context: Context, videoId: String, imageView: ImageView) {
      Glide.with(context)
          .load(String.format(YOUTUBE_IMG_URL, videoId))
          .into(imageView)
    }

    fun loadPoster(context: Context, posterPath: String, imageView: ImageView) {
      Glide.with(context)
          .load(String.format(MOVIES_IMG_URL, "w342", posterPath))
//          .skipMemoryCache(true)
//          .diskCacheStrategy(DiskCacheStrategy.SOURCE)
          .into(imageView)
    }

    fun loadBackdrop(context: Context, backdropPath: String, imageView: ImageView) {
      Glide.with(context)
          .load(String.format(MOVIES_IMG_URL, "w780", backdropPath))
              .into(imageView)
//          .centerCrop()
//          .into(object : SimpleTarget<GlideDrawable>() {
//            override fun onResourceReady(resource: GlideDrawable, glideAnimation: GlideAnimation<in GlideDrawable>) {
//              imageView.setImageDrawable(resource)
//            }
//          })
    }
  }
}
