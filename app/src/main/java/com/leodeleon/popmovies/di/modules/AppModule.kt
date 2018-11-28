package com.leodeleon.popmovies.di.modules

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import com.leodeleon.popmovies.BuildConfig
import com.leodeleon.popmovies.data.local.PopMoviesDB
import com.leodeleon.popmovies.di.ViewModelFactory
import com.leodeleon.popmovies.di.component.ViewModelSubComponent
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import okhttp3.Cache
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(subcomponents = arrayOf(ViewModelSubComponent::class))
class AppModule(internal val application: Application) {

  @Provides
  @Singleton
  internal fun provideApplication(): Application {
    return application
  }

  @Provides
  @Singleton
  internal fun provideApplicationContext(): Context {
    return application.applicationContext
  }

  @Provides
  @Singleton
  internal fun provideOkHttpClient(): OkHttpClient {
    val networkInterceptor = { chain: Chain ->
      val original = chain.request()
      val originalHttpUrl = original.url()

      val url = originalHttpUrl.newBuilder()
          .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
          .build()

      val requestBuilder = original.newBuilder().url(url)

      val request = requestBuilder.build()
      chain.proceed(request)
    }
    val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    val cacheSize = 10 * 1024 * 1024
    val cache = Cache(application.cacheDir, cacheSize.toLong())

    return OkHttpClient.Builder()
        .addInterceptor(networkInterceptor)
        .addInterceptor(loggingInterceptor)
        .cache(cache)
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .build()
  }

  @Singleton
  @Provides
  internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  @Singleton
  @Provides
  internal fun providesMovieSubject(): PublishSubject<Movie> {
    return PublishSubject.create<Movie>()
  }

  @Singleton
  @Provides
  internal fun provideDb(): PopMoviesDB {
    return Room.databaseBuilder(application, PopMoviesDB::class.java, "movie.db").build()
  }

  @Singleton
  @Provides
  internal fun provideViewModelFactory(
      viewModelSubComponent: ViewModelSubComponent.Builder): ViewModelProvider.Factory {
    return ViewModelFactory(viewModelSubComponent.build())
  }
}