package com.leodeleon.popmovies.di;

import com.leodeleon.popmovies.feature.MoviesFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module public abstract class FragmentBuildersModule {
  @ContributesAndroidInjector
  abstract MoviesFragment moviesFragment();

}
