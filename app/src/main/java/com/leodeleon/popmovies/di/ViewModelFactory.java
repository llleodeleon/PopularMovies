package com.leodeleon.popmovies.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.util.ArrayMap;
import com.leodeleon.popmovies.di.component.ViewModelSubComponent;
import com.leodeleon.popmovies.feature.viewModel.MovieDetailsViewModel;
import com.leodeleon.popmovies.feature.viewModel.MoviesViewModel;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class ViewModelFactory implements ViewModelProvider.Factory {
  private final ArrayMap<Class, Callable<? extends ViewModel>> creators;

  @Inject
  public ViewModelFactory(ViewModelSubComponent viewModelSubComponent) {
    creators = new ArrayMap<>();
    creators.put(MoviesViewModel.class, viewModelSubComponent::moviesViewModel);
    creators.put(MovieDetailsViewModel.class, viewModelSubComponent::movieDetailsViewModel);
  }

  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    Callable<? extends ViewModel> creator = creators.get(modelClass);
    if (creator == null) {
      for (Map.Entry<Class, Callable<? extends ViewModel>> entry : creators.entrySet()) {
        if (modelClass.isAssignableFrom(entry.getKey())) {
          creator = entry.getValue();
          break;
        }
      }
    }
    if (creator == null) {
      throw new IllegalArgumentException("unknown model class " + modelClass);
    }
    try {
      return (T) creator.call();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
