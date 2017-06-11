package com.leodeleon.popmovies.di.modules

import com.leodeleon.popmovies.feature.view.DetailFragment
import com.leodeleon.popmovies.feature.view.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class FragmentBuildersModule {
  @ContributesAndroidInjector
  internal abstract fun detailFragment(): DetailFragment

  @ContributesAndroidInjector
  internal abstract fun moviesFragment(): MoviesFragment
}
