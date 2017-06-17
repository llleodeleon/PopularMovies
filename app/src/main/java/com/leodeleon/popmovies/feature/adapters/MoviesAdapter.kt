package com.leodeleon.popmovies.feature.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import com.leodeleon.popmovies.feature.common.AdapterConstants
import com.leodeleon.popmovies.feature.common.ViewType
import com.leodeleon.popmovies.feature.common.ViewTypeAdapter
import com.leodeleon.popmovies.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviesAdapter(onClick: (Movie) -> Unit ) : Adapter<ViewHolder>() {

  private var items: ArrayList<ViewType>
  private var viewTypeAdapters = SparseArrayCompat<ViewTypeAdapter>()
  private val disposable = CompositeDisposable()
  private val loadingItem = object : ViewType {
    override fun getViewType() = AdapterConstants.LOADING
  }

  init {
    viewTypeAdapters.put(AdapterConstants.LOADING, LoadingViewTypeAdapter())
    viewTypeAdapters.put(AdapterConstants.MOVIES, MovieViewTypeAdapter(onClick))
    items = ArrayList()
    items.add(loadingItem)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return viewTypeAdapters.get(viewType).onCreateViewHolder(parent)

  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    viewTypeAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
    super.onDetachedFromRecyclerView(recyclerView)
    disposable.clear()
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