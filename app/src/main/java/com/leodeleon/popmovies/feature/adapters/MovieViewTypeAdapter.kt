package com.leodeleon.popmovies.feature.adapters

import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.feature.common.ViewType
import com.leodeleon.popmovies.feature.common.ViewTypeAdapter
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.util.GlideHelper
import com.leodeleon.popmovies.util.inflate
import kotlinx.android.synthetic.main.view_movie.view.poster

class MovieViewTypeAdapter(val onClick: (Movie) -> Unit) : ViewTypeAdapter{

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
    return MovieViewHolder(parent.inflate(R.layout.view_movie, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, item: ViewType) {
    holder as MovieViewHolder
    holder.bindView(item as Movie, onClick)
    holder.itemView.isClickable = true
  }

  inner class MovieViewHolder(itemView: View): ViewHolder(itemView) {
    fun bindView(movie: Movie, onClick: (Movie) -> Unit) = with(itemView) {
      GlideHelper.loadPoster(context, movie.poster_path, poster)
      super.itemView.setOnClickListener {
        onClick(movie)
        Log.d("Recycler", "CLICKED")
      }
    }
  }
}