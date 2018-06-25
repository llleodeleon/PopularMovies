package com.leodeleon.popmovies.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.bumptech.glide.request.transition.Transition

import com.leodeleon.popmovies.util.Constants.MOVIES_IMG_URL
import com.leodeleon.popmovies.util.Constants.YOUTUBE_IMG_URL

/**
 * Created by leodeleon on 10/02/2017.
 */

class GlideHelper {
  companion object {
    fun loadThumbnail(context: Context, videoId: String, imageView: ImageView) {
        val options = RequestOptions().apply{
            centerCrop()
        }
      Glide.with(context)
          .load(String.format(YOUTUBE_IMG_URL, videoId))
              .apply(options)
          .into(imageView)
    }

    fun loadPoster(context: Context, posterPath: String, imageView: ImageView) {
        val options = RequestOptions().apply{
            centerCrop()
            skipMemoryCache(true)
            diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        }
      Glide.with(context)
          .load(String.format(MOVIES_IMG_URL, "w342", posterPath))
              .apply(options)
              .transition(DrawableTransitionOptions.withCrossFade())
              .into(imageView)
    }

    fun loadBackdrop(context: Context, backdropPath: String, imageView: ImageView) {
        val options = RequestOptions().apply{
            centerCrop()
        }
      Glide.with(context)
          .load(String.format(MOVIES_IMG_URL, "w780", backdropPath))
          .apply(options)
          .into(object : SimpleTarget<Drawable>() {
              override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                  imageView.setImageDrawable(resource)
              }
          })
    }
  }
}
