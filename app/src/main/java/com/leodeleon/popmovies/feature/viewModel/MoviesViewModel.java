package com.leodeleon.popmovies.feature.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.leodeleon.popmovies.data.MovieRepository;
import com.leodeleon.popmovies.model.Movie;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;

public class MoviesViewModel extends ViewModel {
  private final MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
  private final CompositeDisposable disposable = new CompositeDisposable();
  private final MovieRepository movieRepository;

  @Inject public MoviesViewModel(MovieRepository repository) {
    movieRepository = repository;
  }

  public void loadPopularMovies(int page) {
    Disposable d1 = movieRepository.getPopMovies(page).subscribe(moviesLiveData::postValue);
    disposable.add(d1);
  }

  public void loadTopRatedMovies(int page) {
    Disposable d2 = movieRepository.getTopMovies(page).subscribe(moviesLiveData::postValue);
    disposable.add(d2);
  }

  public void loadFavoriteMovies() {
    Disposable d3 = movieRepository.getFavMovies().subscribe(moviesLiveData::postValue);
    disposable.add(d3);
  }

  public MutableLiveData<List<Movie>> getMoviesLiveData() {
    return moviesLiveData;
  }

  @Override protected void onCleared() {
    super.onCleared();
    disposable.clear();
  }


}
