package com.leodeleon.popmovies.feature.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.leodeleon.popmovies.data.MovieRepository;
import com.leodeleon.popmovies.model.Movie;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;

@Singleton
public class MoviesViewModel extends ViewModel {
  private final MutableLiveData<List<Movie>> popMoviesLiveData = new MutableLiveData<>();
  private final MutableLiveData<List<Movie>> topMoviesLiveData = new MutableLiveData<>();
  private final MutableLiveData<List<Movie>> favMoviesLiveData = new MutableLiveData<>();
  private final CompositeDisposable disposable = new CompositeDisposable();
  private final MovieRepository movieRepository;
  private final PublishSubject<Movie> movieSubject;

  @Inject public MoviesViewModel(MovieRepository repository, PublishSubject<Movie> subject ) {
    movieRepository = repository;
    movieSubject = subject;
    subscribe();
  }

  public void loadPopularMovies(int page) {
    Disposable d1 = movieRepository.getPopMovies(page).subscribe(popMoviesLiveData::postValue);
    disposable.add(d1);
  }

  public Single<List<Movie>> loadPopMovies(final int page) {
    return movieRepository.getPopMovies(page);
  }

  public void loadTopRatedMovies(int page) {
    Disposable d2 = movieRepository.getTopMovies(page).subscribe(topMoviesLiveData::postValue);
    disposable.add(d2);
  }

  public void loadFavoriteMovies() {
    Disposable d3 = movieRepository.getFavMovies().subscribe(favMoviesLiveData::postValue);
    disposable.add(d3);
  }

  public MutableLiveData<List<Movie>> getFavMoviesLiveData() {
    return favMoviesLiveData;
  }

  public MutableLiveData<List<Movie>> getPopMoviesLiveData() {
    return popMoviesLiveData;
  }

  public MutableLiveData<List<Movie>> getTopMoviesLiveData() {
    return topMoviesLiveData;
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    disposable.clear();
  }

  private void subscribe() {
    Disposable d4 = movieSubject.subscribe(movie -> loadFavoriteMovies(), Timber::e);
    disposable.add(d4);
  }

}
