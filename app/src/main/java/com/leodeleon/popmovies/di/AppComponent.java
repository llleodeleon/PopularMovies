package com.leodeleon.popmovies.di;

import android.app.Application;
import com.leodeleon.popmovies.PopMoviesApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    AndroidInjectionModule.class,
    AppModule.class,
    ActivitiesModule.class
})
public interface AppComponent {

  void inject(PopMoviesApplication application);

  @Component.Builder interface Builder {
    @BindsInstance
    Builder application(Application application);
    Builder appModule(AppModule appModule);
    AppComponent build();
  }
}

