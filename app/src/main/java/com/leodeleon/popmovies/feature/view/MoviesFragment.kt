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
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.di.Injectable
import com.leodeleon.popmovies.feature.adapters.MoviesAdapter
import com.leodeleon.popmovies.feature.common.AdapterConstants
import com.leodeleon.popmovies.feature.common.BaseFragment
import com.leodeleon.popmovies.feature.viewModel.MoviesViewModel
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.util.inflate
import io.reactivex.functions.Consumer
import io.reactivex.processors.PublishProcessor
import kotlinx.android.synthetic.main.fragment_movies.progress_bar
import kotlinx.android.synthetic.main.fragment_movies.recycler_view
import kotlinx.android.synthetic.main.fragment_movies.text_placeholder
import javax.inject.Inject

class MoviesFragment : BaseFragment(), Injectable {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: MoviesViewModel
  private lateinit var layoutManager: GridLayoutManager
  private lateinit var movieData: MovieData

  private val adapter = MoviesAdapter()
  private val paginator = PublishProcessor.create<Int>()
  private val popMoviesData = PopMoviesData()
  private val topMoviesData = TopMoviesData()
  private val favMoviesData = FavMoviesData()

  private var pageNumber = 1
  private var position: Int = 0


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
    movieData.subscribe()

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
    val d1 = RxRecyclerView.scrollEvents(recycler_view).subscribe(ScrollListener())
    disposable.add(d1)
  }

  private fun setRecyclerView() {
    recycler_view.itemAnimator = DefaultItemAnimator()
    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return if (adapter.getItemViewType(position) == AdapterConstants.LOADING) 2 else 1
      }
    }
    recycler_view.adapter = adapter

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
              adapter.setMovies(it)
              progress_bar.visibility = View.GONE
              text_placeholder.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
          }
      )
    }

    override fun subscribe() {
      val d2 = paginator.onBackpressureDrop().subscribe { _ -> viewModel.loadFavoriteMovies() }
      disposable.add(d2)
    }
  }

  inner class ScrollListener : Consumer<RecyclerViewScrollEvent> {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun accept(event: RecyclerViewScrollEvent?) {
      event?.let {
        if (it.dy() > 0) {
          visibleItemCount = layoutManager.childCount
          totalItemCount = layoutManager.itemCount
          firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

          if (loading) {
            if (totalItemCount > previousTotal) {
              loading = false
              previousTotal = totalItemCount
            }
          }
          if (!loading &&
              (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            pageNumber++
            paginator.onNext(pageNumber)
            loading = true
          }
        }
      }

    }


  }

}
