package com.leodeleon.popmovies.feature;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.leodeleon.popmovies.data.MovieRepository;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.model.MoviesResult;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
    Disposable d1 = movieRepository.getPopularMovies(page).subscribe(new Consumer<MoviesResult>() {
      @Override public void accept(@NonNull MoviesResult moviesResult) throws Exception {
        moviesLiveData.postValue(moviesResult.getMovies());
      }
    });
  }


  public MutableLiveData<List<Movie>> getMoviesLiveData() {
    return moviesLiveData;
  }


  @Override protected void onCleared() {
    super.onCleared();
    disposable.clear();
  }
}
