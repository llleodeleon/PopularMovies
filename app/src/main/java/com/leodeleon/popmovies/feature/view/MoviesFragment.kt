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
import com.leodeleon.popmovies.feature.MainActivity
import com.leodeleon.popmovies.feature.adapters.MoviesAdapter
import com.leodeleon.popmovies.feature.common.AdapterConstants
import com.leodeleon.popmovies.feature.common.BaseFragment
import com.leodeleon.popmovies.feature.common.ScrollListener
import com.leodeleon.popmovies.feature.viewModel.MoviesViewModel
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.util.inflate
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import kotlinx.android.synthetic.main.fragment_movies.progress_bar
import kotlinx.android.synthetic.main.fragment_movies.recycler_view
import kotlinx.android.synthetic.main.fragment_movies.text_placeholder
import javax.inject.Inject

class MoviesFragment : BaseFragment(), Injectable {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: MoviesViewModel
  private lateinit var layoutManager: GridLayoutManager
  private lateinit var scrollListener: ScrollListener
  private lateinit var adapter: MoviesAdapter

  private val paginator = PublishProcessor.create<Int>()

  private var pageNumber = 1
  private var position: Int = 0
  private lateinit var moviesData: MoviesData

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


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = container?.inflate(R.layout.fragment_movies, false)
    position = arguments?.getInt(POSITION)?:0
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

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)


    moviesData.observe(viewModel)
    disposable.add(moviesData.subscribe(paginator))

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
    val d1 = RxRecyclerView
        .scrollEvents(recycler_view)
        .subscribe({
          scrollListener.loadMore()
        })
    disposable.add(d1)
  }

  private fun setRecyclerView() {
    adapter = MoviesAdapter {
      (activity as MainActivity).addFragment(DetailFragment.newInstance(it))
    }

    scrollListener = ScrollListener(layoutManager) { paginator.onNext(pageNumber++) }

    recycler_view.itemAnimator = DefaultItemAnimator()
    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return if (adapter.getItemViewType(position) == AdapterConstants.LOADING) 2 else 1
      }
    }
    recycler_view.adapter = adapter

  }

  fun setMoviesData() {
    val observePop = { viewModel: MoviesViewModel ->
      viewModel.popMoviesLiveData.observe(this,
          Observer<List<Movie>> { movies ->
            movies?.let {
              setData(it)
            }
          })
    }

    val subscribePop = { processor: PublishProcessor<Int> ->
      processor.onBackpressureDrop().subscribe { page -> viewModel.loadPopularMovies(page) }
    }

    val observeTop = { viewModel: MoviesViewModel ->
      viewModel.topMoviesLiveData.observe(this,
          Observer<List<Movie>> { movies ->
            movies?.let {
              setData(it)
            }
          })
    }

    val subscribeTop = { processor: PublishProcessor<Int> ->
      processor.onBackpressureDrop().subscribe { page -> viewModel.loadTopRatedMovies(page) }
    }

    val observeFav = { viewModel: MoviesViewModel ->
      viewModel.favMoviesLiveData.observe(this,
          Observer<List<Movie>> { movies1 ->
            movies1?.let {
              adapter.setMovies(it)
              progress_bar.visibility = View.GONE
              text_placeholder.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
          }
      )
    }

    val subscribeFav = { processor: PublishProcessor<Int> ->
      processor.onBackpressureDrop().subscribe { page -> viewModel.loadFavoriteMovies() }
    }


    when (position) {
      POSITION_POPULAR -> moviesData = MoviesData(observePop, subscribePop)
      POSITION_RATED -> moviesData = MoviesData(observeTop, subscribeTop)
      POSITION_FAVORITE -> moviesData = MoviesData(observeFav, subscribeFav)
    }
  }

  class MoviesData(val observer: (MoviesViewModel) -> Unit, val subscriber: (PublishProcessor<Int>) -> Disposable){
    fun observe(viewModel: MoviesViewModel) {
      observer.invoke(viewModel)
    }

    fun subscribe(processor: PublishProcessor<Int>) = subscriber.invoke(processor)

  }

}
