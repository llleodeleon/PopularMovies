package com.leodeleon.popmovies.feature.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.di.Injectable
import com.leodeleon.popmovies.feature.MainActivity
import com.leodeleon.popmovies.feature.adapters.TrailerAdapter
import com.leodeleon.popmovies.feature.common.BaseFragment
import com.leodeleon.popmovies.feature.viewModel.MovieDetailsViewModel
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.model.MovieDetail
import com.leodeleon.popmovies.util.GlideHelper
import com.leodeleon.popmovies.util.inflate
import com.leodeleon.popmovies.util.snack
import com.robertlevonyan.views.chip.Chip
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_detail.backdrop
import kotlinx.android.synthetic.main.fragment_detail.collapsing_toolbar
import kotlinx.android.synthetic.main.fragment_detail.fab
import kotlinx.android.synthetic.main.fragment_detail.toolbar
import kotlinx.android.synthetic.main.layout_detail.flow_layout
import kotlinx.android.synthetic.main.layout_detail.poster
import kotlinx.android.synthetic.main.layout_detail.recycler_view
import kotlinx.android.synthetic.main.layout_detail.text_overview
import kotlinx.android.synthetic.main.layout_detail.text_release_date
import kotlinx.android.synthetic.main.layout_detail.text_runtime
import kotlinx.android.synthetic.main.layout_detail.text_trailers
import kotlinx.android.synthetic.main.layout_detail.text_vote_average
import org.apmem.tools.layouts.FlowLayout
import java.util.Locale
import javax.inject.Inject

class DetailFragment : BaseFragment(), Injectable {

  @Inject lateinit internal var viewModelFactory: ViewModelProvider.Factory
  @Inject lateinit internal var movieSubject: PublishSubject<Movie>

  private lateinit var viewModel: MovieDetailsViewModel
  private lateinit var movie: Movie

  private val adapter = TrailerAdapter()

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = container?.inflate(R.layout.fragment_detail, false)
    movie = arguments.getParcelable<Movie>(MOVIE)
    return view
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    bindMovie()
    setRecyclerView()
    setToolbar()
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel::class.java)
    savedInstanceState?: viewModel.loadDetails(movie.id)
    observeLiveData()
    subscribe()
  }

  private fun setToolbar() {
    toolbar.background = context.resources.getDrawable(R.drawable.toolbar_gradient)
    (activity as MainActivity).setSupportActionBar(toolbar)
    (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun observeLiveData() {
    viewModel.videoLiveData.observe(this, Observer { videoIds ->
      videoIds?.let {
        adapter.setVideoKeys(videoIds)
      }
      videoIds?: run {
        recycler_view.visibility = View.GONE
        text_trailers.visibility = View.GONE
      }
    })

    viewModel.detailLiveData.observe(this, Observer { movieDetail ->
      movieDetail?.let {
        bindDetails(movieDetail)
      }
    })
  }

  private fun subscribe() {
    val d1 = RxView.clicks(fab).subscribe { _ ->
      val isSelected = !fab.isSelected
      fab.isSelected = isSelected
      if (isSelected) {
        viewModel.addFavorite(movie)
      } else {
        viewModel.removeFavorite(movie)
      }
    }

    val d2 = movieSubject.subscribe { movie1 ->
      val added = context.getString(R.string.movie_added)
      val removed = context.getString(R.string.movie_removed)
      val string = if (movie1.isFavorite()) added else removed
      val color = ContextCompat.getColor(context,R.color.colorPrimaryDark)
      view?.snack(string){ getView().setBackgroundColor(color) }
    }

    disposable.add(d1)
    disposable.add(d2)
  }

  private fun setRecyclerView() {
    recycler_view.adapter = adapter
  }

  private fun bindMovie() {
    val voting = "%1$.1f/10"

    GlideHelper.loadBackdrop(context, movie.backdrop_path, backdrop)
    GlideHelper.loadPoster(context, movie.poster_path, poster)
    collapsing_toolbar.title = movie.title
    text_vote_average.text =  String.format(Locale.getDefault(), voting, movie.vote_average)
    text_release_date.text = movie.release_date.substring(0, 4)
    text_overview.text = movie.overview
    fab.isSelected = movie.isFavorite()
  }

  private fun bindDetails(movieDetail: MovieDetail) {
    val runtime = "%dmin"
    text_runtime.text = String.format(Locale.getDefault(), runtime, movieDetail.runtime)
    flow_layout.removeAllViews()
     val margin = context.resources.getDimension(R.dimen.dp8).toInt()

    for ((_, name) in movieDetail.genres) {
      val chip = flow_layout?.inflate(R.layout.view_chip, false) as Chip
      val params = FlowLayout.LayoutParams(
          FlowLayout.LayoutParams.WRAP_CONTENT,
          FlowLayout.LayoutParams.WRAP_CONTENT
      )
      params.setMargins(0, 0, margin, margin)
      chip.layoutParams = params
      chip.chipText = name
      flow_layout.addView(chip)
    }
  }

  companion object {
    val MOVIE = "MOVIE"

    fun newInstance(movie: Movie): DetailFragment {
      val args = Bundle()
      val fragment = DetailFragment()
      args.putParcelable(MOVIE, movie)
      fragment.arguments = args
      return fragment
    }
  }
}