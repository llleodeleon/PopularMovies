package com.leodeleon.popmovies.di.component;

import com.leodeleon.popmovies.feature.viewModel.MovieDetailsViewModel;
import com.leodeleon.popmovies.feature.viewModel.MoviesViewModel;
import dagger.Subcomponent;

@Subcomponent public interface ViewModelSubComponent {
  MoviesViewModel moviesViewModel();
  MovieDetailsViewModel movieDetailsViewModel();

  @Subcomponent.Builder interface Builder {
    ViewModelSubComponent build();
  }
}
