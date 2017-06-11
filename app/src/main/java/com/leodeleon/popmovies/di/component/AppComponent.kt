package com.leodeleon.popmovies.di.component

import android.app.Application
import com.leodeleon.popmovies.PopMoviesApplication
import com.leodeleon.popmovies.di.modules.ActivitiesModule
import com.leodeleon.popmovies.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, ActivitiesModule::class))
interface AppComponent {

  fun inject(application: PopMoviesApplication)

  @Component.Builder interface Builder {
    @BindsInstance
    fun application(application: Application): Builder
    fun appModule(appModule: AppModule): Builder
    fun build(): AppComponent
  }
}

