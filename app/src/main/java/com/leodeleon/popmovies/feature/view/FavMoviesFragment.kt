package com.leodeleon.popmovies.feature.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.feature.adapters.MoviesAdapter
import com.leodeleon.popmovies.feature.common.AdapterConstants
import com.leodeleon.popmovies.feature.common.BaseFragment
import com.leodeleon.popmovies.feature.common.ScrollListener
import com.leodeleon.popmovies.feature.viewModel.FavMoviesViewModel
import com.leodeleon.popmovies.feature.viewModel.PopMoviesViewModel
import com.leodeleon.popmovies.feature.viewModel.TopMoviesViewModel
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.util.Constants.LAYOUT_MANAGER_STATE
import com.leodeleon.popmovies.util.inflate
import com.leodeleon.popmovies.util.observe
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_movies.progress_bar
import kotlinx.android.synthetic.main.fragment_movies.recycler_view
import org.koin.android.architecture.ext.viewModel

class FavMoviesFragment : BaseFragment() {

  private val viewModel: FavMoviesViewModel by viewModel()
  private lateinit var layoutManager: GridLayoutManager
  private lateinit var scrollListener: ScrollListener
  private lateinit var adapter: MoviesAdapter

  private val paginator = PublishProcessor.create<Int>()

  private var pageNumber = 1

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = container?.inflate(R.layout.fragment_movies, false)
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    layoutManager = recycler_view.layoutManager as GridLayoutManager
    progress_bar.visibility = View.VISIBLE
    setMoviesData()
    setRecyclerView()
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    subscribe()
    if (savedInstanceState == null) {
      paginator.onNext(pageNumber)
    }
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    savedInstanceState?.let {
      val state = it.getParcelable<Parcelable>(LAYOUT_MANAGER_STATE)
      layoutManager.onRestoreInstanceState(state)
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManager.onSaveInstanceState())
    super.onSaveInstanceState(outState)
  }

  private fun setData(movieList: List<Movie>) {
    adapter.addMovies(movieList)
    progress_bar.visibility = View.GONE
  }

  private fun subscribe() {
   RxRecyclerView
        .scrollEvents(recycler_view)
        .subscribe {
          scrollListener.loadMore()
        }
				.addTo(subscriptions)
  }

  private fun setRecyclerView() {
    adapter = MoviesAdapter {
      //(activity as MainActivity).addFragment(DetailFragment.newInstance(it))
    }

    scrollListener = ScrollListener(layoutManager) { paginator.onNext(pageNumber++) }


    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return if (adapter.getItemViewType(position) == AdapterConstants.LOADING) 2 else 1
      }
    }
    recycler_view.adapter = adapter
  }

  fun setMoviesData() {

		observe(viewModel.favMoviesLiveData){
			setData(it)
		}

		paginator.onBackpressureDrop()
				.subscribe { viewModel.loadFavoriteMovies() }
				.addTo(subscriptions)
  }


}
