package com.leodeleon.popmovies.di;

import com.leodeleon.popmovies.feature.MoviesViewModel;
import dagger.Subcomponent;

@Subcomponent public interface ViewModelSubComponent {
  MoviesViewModel moviesViewModel();

  @Subcomponent.Builder interface Builder {
    ViewModelSubComponent build();
  }
}
