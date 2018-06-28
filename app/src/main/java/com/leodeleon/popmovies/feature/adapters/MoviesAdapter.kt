package com.leodeleon.popmovies.feature.adapters

import android.arch.paging.PagedListAdapter
import android.support.v4.util.SparseArrayCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.feature.common.AdapterConstants
import com.leodeleon.popmovies.feature.common.ViewType
import com.leodeleon.popmovies.feature.common.ViewTypeAdapter
import com.leodeleon.popmovies.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviesAdapter(val onClick: (View, Movie) -> Unit ) : PagedListAdapter<Movie,MovieViewTypeAdapter.MovieViewHolder>(COMPARATOR) {

  companion object {
  	private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
      override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
       return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
      }
    }
  }

//  private var items: ArrayList<ViewType>
  private var viewTypeAdapters = SparseArrayCompat<ViewTypeAdapter>()
//  private val disposable = CompositeDisposable()
  private val loadingItem = object : ViewType {
    override fun getViewType() = AdapterConstants.LOADING
  }

  init {
//    viewTypeAdapters.put(AdapterConstants.LOADING, LoadingViewTypeAdapter())
//    viewTypeAdapters.put(AdapterConstants.MOVIES, MovieViewTypeAdapter(onClick))
//    items = ArrayList()
//    items.add(loadingItem)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewTypeAdapter.MovieViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_movie, parent, false)
    return MovieViewTypeAdapter.MovieViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: MovieViewTypeAdapter.MovieViewHolder, position: Int) {
    holder.bindView(getItem(position), onClick)

//    viewTypeAdapters.get(getItemViewType(position)).onBindViewHolder(holder, getItem(position))
  }

//  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
//    super.onDetachedFromRecyclerView(recyclerView)
//    disposable.clear()
//  }
//
//  override fun getItemCount(): Int {
//    return items.size
//  }

//  override fun getItemViewType(position: Int): Int {
//    return this.items.get(position).getViewType()
//  }

  fun addMovies(newMovies: List<Movie>) {
//    val initPosition = items.size - 1
//    items.removeAt(initPosition)
//    notifyItemRemoved(initPosition)
//    items.addAll(newMovies)
//    items.add(loadingItem)
//    notifyItemRangeChanged(initPosition, items.size + 1)
  }

//  fun setMovies(newMovies: List<Movie>) {
//    items.clear()
//    items.addAll(newMovies)
//    notifyDataSetChanged()
//  }
}