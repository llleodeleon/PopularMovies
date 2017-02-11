package com.leodeleon.popmovies.api;


import android.content.Context;

import com.leodeleon.popmovies.BuildConfig;
import com.leodeleon.popmovies.util.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by leodeleon on 08/02/2017.
 */

public class API {

  private static API instance = null;
  private Retrofit retrofit;
  private OkHttpClient okHttpClient;

  public static API getInstance() {
    return instance;
  }

  public static API initialize(final Context context) {
    instance = new API();
    instance.okHttpClient = new OkHttpClient.Builder().addInterceptor( new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
          .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
          .build();

        Request.Builder requestBuilder = original.newBuilder()
          .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
      }
    }).build();

    instance.retrofit = new Retrofit
      .Builder()
      .baseUrl(Constants.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(instance.okHttpClient)
      .build();
    return instance;
  }

  public Retrofit getRetrofit() {
    return retrofit;
  }
}
