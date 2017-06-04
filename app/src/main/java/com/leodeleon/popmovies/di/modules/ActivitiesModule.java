package com.leodeleon.popmovies.di.modules;

import com.leodeleon.popmovies.feature.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module public abstract class ActivitiesModule {

  @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
  abstract MainActivity contributeMainActivity();
}
