package com.leodeleon.popmovies.feature.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import com.leodeleon.popmovies.feature.common.AdapterConstants
import com.leodeleon.popmovies.feature.common.ViewType
import com.leodeleon.popmovies.feature.common.ViewTypeAdapter
import com.leodeleon.popmovies.model.Movie

class MoviesAdapter : Adapter<ViewHolder>() {

  private var items: ArrayList<ViewType>
  private var viewTypeAdapters = SparseArrayCompat<ViewTypeAdapter>()
  private val loadingItem = object : ViewType {
    override fun getViewType() = AdapterConstants.LOADING
  }

  init {
    viewTypeAdapters.put(AdapterConstants.LOADING, LoadingViewTypeAdapter())
    viewTypeAdapters.put(AdapterConstants.MOVIES, MovieViewTypeAdapter())
    items = ArrayList()
    items.add(loadingItem)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val type = viewTypeAdapters.get(viewType)
    val holder =  type.onCreateViewHolder(parent)
    return holder

  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    viewTypeAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])

  }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun getItemViewType(position: Int): Int {
    return this.items.get(position).getViewType()
  }

  fun addMovies(newMovies: List<Movie>) {
    val initPosition = items.size - 1
    items.removeAt(initPosition)
    notifyItemRemoved(initPosition)
    items.addAll(newMovies)
    items.add(loadingItem)
    notifyItemRangeChanged(initPosition, items.size + 1)
  }

  fun setMovies(newMovies: List<Movie>) {
    items.clear()
    items.addAll(newMovies)
    notifyDataSetChanged()
  }
}