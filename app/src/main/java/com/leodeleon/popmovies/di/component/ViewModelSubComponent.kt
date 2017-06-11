package com.leodeleon.popmovies.di.component

import com.leodeleon.popmovies.feature.viewModel.MovieDetailsViewModel
import com.leodeleon.popmovies.feature.viewModel.MoviesViewModel
import dagger.Subcomponent

@Subcomponent interface ViewModelSubComponent {

  fun moviesViewModel(): MoviesViewModel

  fun movieDetailsViewModel(): MovieDetailsViewModel

  @Subcomponent.Builder interface Builder {
    fun build(): ViewModelSubComponent
  }
}
