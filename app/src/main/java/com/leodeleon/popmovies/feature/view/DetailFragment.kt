package com.leodeleon.popmovies.feature.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.feature.MainActivity
import com.leodeleon.popmovies.feature.adapters.TrailerAdapter
import com.leodeleon.popmovies.feature.common.BaseFragment
import com.leodeleon.popmovies.feature.viewModel.MovieDetailsViewModel
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.model.MovieDetail
import com.leodeleon.popmovies.util.GlideHelper
import com.leodeleon.popmovies.util.inflate
import com.leodeleon.popmovies.util.snack
//import com.robertlevonyan.views.chip.Chip
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.layout_detail.*
//import org.apmem.tools.layouts.FlowLayout
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject
import java.util.*

class DetailFragment : BaseFragment() {

  private val movieSubject: PublishSubject<Movie> by inject()
  private val viewModel: MovieDetailsViewModel by viewModel()

  private var movie: Movie? = null

  private val adapter = TrailerAdapter()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = container?.inflate(R.layout.fragment_detail, false)
    movie = arguments?.getParcelable<Movie>(MOVIE)
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    bindMovie()
    setRecyclerView()
    setToolbar()
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    val movie = this.movie ?: return
    savedInstanceState?: viewModel.loadDetails(movie.id)
    observeLiveData()
    subscribe()
  }

  private fun setToolbar() {
    val ctx = context?: return
    toolbar.background = ContextCompat.getDrawable(ctx, R.drawable.toolbar_gradient)
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
    val movie = this.movie ?: return
    val ctx = context?: return
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
      val added = ctx.getString(R.string.movie_added)
      val removed = ctx.getString(R.string.movie_removed)
      val string = if (movie1.isFavorite()) added else removed
      val color = ContextCompat.getColor(ctx,R.color.colorPrimaryDark)
      view?.snack(string){ view.setBackgroundColor(color) }
    }

    subscriptions.add(d1)
    subscriptions.add(d2)
  }

  private fun setRecyclerView() {
    recycler_view.adapter = adapter
  }

  private fun bindMovie() {
    val voting = "%1$.1f/10"
    val ctx = context?: return
    val movie = this.movie ?: return
    GlideHelper.loadBackdrop(ctx, movie.backdrop_path, backdrop)
    GlideHelper.loadPoster(ctx, movie.poster_path, poster)
    collapsing_toolbar.title = movie.title
    text_vote_average.text =  String.format(Locale.getDefault(), voting, movie.vote_average)
    text_release_date.text = movie.release_date.substring(0, 4)
    text_overview.text = movie.overview
    fab.isSelected = movie.isFavorite()
  }

  private fun bindDetails(movieDetail: MovieDetail) {
    val runtime = "%dmin"
    text_runtime.text = String.format(Locale.getDefault(), runtime, movieDetail.runtime)
      chip_group.removeAllViews()
      movieDetail.genres.map {
          val chip = chip_group.inflate(R.layout.view_chip, false) as Chip
        chip.chipText = it.name
          chip_group.addView(chip)
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