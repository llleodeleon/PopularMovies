package com.leodeleon.popmovies.di.modules

import com.leodeleon.popmovies.feature.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class ActivitiesModule {

  @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
  internal abstract fun contributeMainActivity(): MainActivity
}
