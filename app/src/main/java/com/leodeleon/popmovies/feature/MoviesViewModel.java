package com.leodeleon.popmovies.feature;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.leodeleon.popmovies.data.MovieRepository;
import com.leodeleon.popmovies.model.Movie;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;

public class MoviesViewModel extends ViewModel {
  private MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
  private CompositeDisposable disposable = new CompositeDisposable();
  private MovieRepository movieRepository;

  @Inject
  public MoviesViewModel(MovieRepository repository) {
    movieRepository = repository;
  }

  public void loadPopularMovies(int page) {
    Disposable d1 = movieRepository.getPopularMovies(page).subscribe(
        moviesResult -> moviesLiveData.postValue(moviesResult.getMovies()));
    disposable.add(d1);
  }


  public MutableLiveData<List<Movie>> getMoviesLiveData() {
    return moviesLiveData;
  }


  @Override protected void onCleared() {
    super.onCleared();
    disposable.clear();
  }
}
