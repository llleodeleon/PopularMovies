package com.leodeleon.popmovies;

import android.arch.lifecycle.MutableLiveData;
import com.leodeleon.popmovies.data.MovieRepository;
import com.leodeleon.popmovies.feature.viewModel.MoviesViewModel;
import com.leodeleon.popmovies.model.Movie;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MoviesViewModelTest {

  MoviesViewModel moviesViewModel;
  @Mock MovieRepository movieRepository;
  List<Movie> FULL_LIST_OF_MOVIES = Arrays.asList(new Movie(), new Movie());
  List<Movie> EMPTY_LIST_OF_MOVIES = Collections.emptyList();
  @Mock
  MutableLiveData<List<Movie>> movies;

  @Before
  public void setup() {
    RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

    moviesViewModel = new MoviesViewModel(movieRepository, PublishSubject.create());
  }

  @Test
  public void shouldLoadPopularMovies() {
    Mockito.when(movieRepository.getPopMovies(1)).thenReturn(Single.just(FULL_LIST_OF_MOVIES));

    moviesViewModel.loadPopMovies(1).subscribe(movies::postValue);

    Mockito.verify(movies).postValue(FULL_LIST_OF_MOVIES);
  }

  @After
  public void cleanUp() {
    RxJavaPlugins.reset();
    RxAndroidPlugins.reset();
  }
}
