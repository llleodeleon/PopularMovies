package com.leodeleon.popmovies.feature.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.di.Injectable
import com.leodeleon.popmovies.feature.adapters.LoaderAdapter
import com.leodeleon.popmovies.feature.adapters.MovieAdapter
import com.leodeleon.popmovies.feature.common.BaseFragment
import com.leodeleon.popmovies.feature.viewModel.MoviesViewModel
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.util.inflate
import io.reactivex.processors.PublishProcessor
import kotlinx.android.synthetic.main.fragment_movies.progress_bar
import kotlinx.android.synthetic.main.fragment_movies.recycler_view
import kotlinx.android.synthetic.main.fragment_movies.text_placeholder
import javax.inject.Inject

class MoviesFragment : BaseFragment(), Injectable {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private var adapter = MovieAdapter()
  private lateinit var viewModel: MoviesViewModel
  private lateinit var layoutManager: GridLayoutManager
  private val paginator = PublishProcessor.create<Int>()
  private var pageNumber = 1
  private var lastVisibleItem: Int = 0
  private var totalItemCount: Int = 0
  private var visibleThreshold: Int = 0
  private var position: Int = 0
  private lateinit var movieData: MovieData
  private val popMoviesData = PopMoviesData()
  private val topMoviesData = TopMoviesData()
  private val favMoviesData = FavMoviesData()

  companion object {
    private val POSITION = "position"
    private val LAYOUT_MANAGER_STATE = "state"
    val POSITION_POPULAR = 0
    val POSITION_RATED = 1
    val POSITION_FAVORITE = 2

    fun newInstance(position: Int): MoviesFragment {
      val fragment = MoviesFragment()
      val bundle = Bundle()
      bundle.putInt(POSITION, position)
      fragment.arguments = bundle
      return fragment
    }
  }


  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = container?.inflate(R.layout.fragment_movies, false)
    position = arguments.getInt(POSITION)
    return view
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    layoutManager = recycler_view.layoutManager as GridLayoutManager
    progress_bar.visibility = View.VISIBLE
    setMovieData()
    setRecyclerView()
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)

    movieData.observeLiveData()

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

  override fun onSaveInstanceState(outState: Bundle?) {
    outState?.putParcelable(LAYOUT_MANAGER_STATE, layoutManager.onSaveInstanceState())
    super.onSaveInstanceState(outState)
  }

  private fun setMovieData() {
    when (position) {
      POSITION_POPULAR -> movieData = popMoviesData
      POSITION_RATED -> movieData = topMoviesData
      POSITION_FAVORITE -> movieData = favMoviesData
    }
  }

  private fun setData(movieList: List<Movie>) {
    adapter.addMovies(movieList)
    progress_bar.visibility = View.GONE
  }

  private fun subscribe() {
    val d1 = RxRecyclerView.scrollEvents(recycler_view).subscribe { _ ->
      totalItemCount = layoutManager.itemCount - adapter.footerItemCount
      lastVisibleItem = layoutManager.findLastVisibleItemPosition()
      visibleThreshold = layoutManager.spanCount
      val shouldLoadMore = !adapter.isLoading &&
          totalItemCount > 0 &&
          lastVisibleItem + visibleThreshold > totalItemCount &&
          position != POSITION_FAVORITE

      if (shouldLoadMore) {
        pageNumber++
        adapter.startLoading()
        paginator.onNext(pageNumber)
      }
    }
    movieData.subscribe()
    disposable.add(d1)
  }

  private fun setRecyclerView() {
    adapter.setHasStableIds(true)
    recycler_view.itemAnimator = DefaultItemAnimator()
    recycler_view.adapter = adapter
    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return if (adapter.getItemViewType(position) == LoaderAdapter.VIEW_TYPE_FOOTER) 2 else 1
      }
    }
  }

  internal interface MovieData {
    fun observeLiveData()
    fun subscribe()
  }

  internal inner class PopMoviesData : MovieData {

    override fun observeLiveData() {
      viewModel.popMoviesLiveData.observe(this@MoviesFragment,
          Observer<List<Movie>> { movies ->
            movies?.let {
              this@MoviesFragment.setData(it)
            }
          })
    }

    override fun subscribe() {
      val d2 = paginator.onBackpressureDrop().subscribe { page -> viewModel.loadPopularMovies(page) }
      disposable.add(d2)
    }
  }

  internal inner class TopMoviesData : MovieData {

    override fun observeLiveData() {
      viewModel.topMoviesLiveData.observe(this@MoviesFragment, Observer<List<Movie>> { movies ->
        movies?.let {
          this@MoviesFragment.setData(it)
        }

      })
    }

    override fun subscribe() {
      val d2 = paginator.onBackpressureDrop().subscribe { page -> viewModel.loadTopRatedMovies(page) }
      disposable.add(d2)
    }
  }

  internal inner class FavMoviesData : MovieData {

    override fun observeLiveData() {
      viewModel.favMoviesLiveData.observe(this@MoviesFragment,
          Observer<List<Movie>> { movies1 ->
            movies1?.let {
              this@MoviesFragment.setData(it)
              text_placeholder.visibility = if (it.size == 0) View.VISIBLE else View.GONE
            }
          }
      )
    }

    override fun subscribe() {
      val d2 = paginator.onBackpressureDrop().subscribe { _ -> viewModel.loadFavoriteMovies() }
      disposable.add(d2)
    }
  }

}
