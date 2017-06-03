package com.leodeleon.popmovies.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;
import com.leodeleon.popmovies.BuildConfig;
import com.leodeleon.popmovies.data.PopMoviesDB;
import com.readystatesoftware.chuck.ChuckInterceptor;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.leodeleon.popmovies.util.Constants.BASE_URL;

@Module(subcomponents = ViewModelSubComponent.class)
public class AppModule {
  final Application application;

  public AppModule(Application application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Application provideApplication() {
    return application;
  }

  @Provides
  @Singleton Context provideApplicationContext() {
    return application.getApplicationContext();
  }


  @Singleton
  @Provides Retrofit provideRetrofit(Context context) {

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(chain -> {
          Request original = chain.request();
          HttpUrl originalHttpUrl = original.url();

          HttpUrl url = originalHttpUrl.newBuilder()
              .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
              .build();

          Request.Builder requestBuilder = original.newBuilder().url(url);

          Request request = requestBuilder.build();
          return chain.proceed(request);
        })
        .addInterceptor(new ChuckInterceptor(context))
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .build();

    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();


  }

  @Singleton
  @Provides PopMoviesDB provideDb() {
    return Room.databaseBuilder(application, PopMoviesDB.class, "movie.db").build();
  }

  @Singleton
  @Provides
  ViewModelProvider.Factory provideViewModelFactory(ViewModelSubComponent.Builder viewModelSubComponent) {
    return new ViewModelFactory(viewModelSubComponent.build());
  }
}