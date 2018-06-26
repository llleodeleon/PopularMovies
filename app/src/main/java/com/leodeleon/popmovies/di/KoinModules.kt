package com.leodeleon.popmovies.di

import android.arch.persistence.room.Room
import com.leodeleon.popmovies.BuildConfig
import com.leodeleon.popmovies.data.MovieRepository
import com.leodeleon.popmovies.data.local.MovieDB
import com.leodeleon.popmovies.data.local.PopMoviesDB
import com.leodeleon.popmovies.data.remote.MovieAPI
import com.leodeleon.popmovies.feature.viewModel.FavMoviesViewModel
import com.leodeleon.popmovies.feature.viewModel.MovieDetailsViewModel
import com.leodeleon.popmovies.feature.viewModel.PopMoviesViewModel
import com.leodeleon.popmovies.feature.viewModel.TopMoviesViewModel
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.util.Constants
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.subjects.PublishSubject
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val appModule = applicationContext {
    viewModel { PopMoviesViewModel(get()) }
    viewModel { TopMoviesViewModel(get()) }
    viewModel { FavMoviesViewModel(get(),get()) }
    viewModel { MovieDetailsViewModel(get(), get()) }

    bean { MovieRepository(get(), get()) }

    bean { createWebService<MovieAPI.MovieService>(get()) }

    bean { MovieAPI(get()) }

    bean { MovieDB(get()) }

    bean { PublishSubject.create<Movie>() }

    bean { Room.databaseBuilder(androidApplication(), PopMoviesDB::class.java, "movie.db").build() }

    bean {
        val networkInterceptor = { chain: Interceptor.Chain ->
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
        val cache = Cache( androidApplication().cacheDir, cacheSize.toLong())

        return@bean OkHttpClient.Builder()
                .addInterceptor(networkInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(ChuckInterceptor(androidApplication()))
                .cache(cache)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build()
    }
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}