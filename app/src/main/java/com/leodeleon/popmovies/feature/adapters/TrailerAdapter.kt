package com.leodeleon.popmovies.feature.adapters

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.google.android.youtube.player.YouTubeApiServiceUtil
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.jakewharton.rxbinding2.view.RxView
import com.leodeleon.popmovies.BuildConfig
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.feature.MainActivity
import com.leodeleon.popmovies.util.Constants.YOUTUBE_BASE_URL
import com.leodeleon.popmovies.util.GlideHelper
import com.leodeleon.popmovies.util.inflate
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_trailer.view.image_thumbnail
import java.util.ArrayList


/**
 * Created by leodeleon on 10/02/2017.
 */

class TrailerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var videoKeys: List<String> = ArrayList()
  val disposable = CompositeDisposable();

  fun setVideoKeys(videoKeys: List<String>) {
    this.videoKeys = videoKeys
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
    return TrailerViewHolder(parent.inflate(R.layout.view_trailer, false))
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as TrailerViewHolder).bindView(videoKeys[position])
  }

  override fun getItemCount(): Int {
    return videoKeys.size
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    disposable.clear()
  }

  inner class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

   lateinit var videoId: String

    fun bindView(videoId: String) {
      this.videoId = videoId
      GlideHelper.loadThumbnail(itemView.context, videoId, itemView.image_thumbnail )
      val d1 = RxView.clicks(itemView).subscribe {
        val activity = itemView.context as MainActivity
        val result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(activity)
        if (result == YouTubeInitializationResult.SUCCESS) {
          openYoututbe(activity)
        } else {
          openCustomTab(activity)
        }

      }
      disposable.add(d1)
    }

    private fun openYoututbe(activity: Activity) {
      activity.startActivity(
          YouTubeStandalonePlayer.createVideoIntent(activity, BuildConfig.GOOGLE_APIS_KEY, videoId, 0, false, true)
      )
    }

    private fun openCustomTab(context: Context) {
      val builder = CustomTabsIntent.Builder()
      builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
      builder.addDefaultShareMenuItem()
      val customTabsIntent = builder.build()
      customTabsIntent.launchUrl(context, Uri.parse(String.format(YOUTUBE_BASE_URL, videoId)))
    }
  }
}
