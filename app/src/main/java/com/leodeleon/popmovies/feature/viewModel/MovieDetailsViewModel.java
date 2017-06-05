package com.leodeleon.popmovies.feature.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.leodeleon.popmovies.data.MovieRepository;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.model.MovieDetail;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;

@Singleton
public class MovieDetailsViewModel extends ViewModel {

  private final MutableLiveData<MovieDetail> detailLiveData = new MutableLiveData<>();
  private final MutableLiveData<List<String>> videoLiveData = new MutableLiveData<>();
  private final MovieRepository movieRepository;
  private final CompositeDisposable disposable = new CompositeDisposable();
  private final PublishSubject<Movie> movieSubject;

  @Inject
  public MovieDetailsViewModel(MovieRepository repository, PublishSubject<Movie> subject) {
    movieRepository = repository;
    movieSubject = subject;
  }

  @Override protected void onCleared() {
    super.onCleared();
    disposable.clear();
  }

  public void loadDetails(int id) {
    Disposable d1 = movieRepository.getMovieDetail(id).subscribe(detailLiveData::postValue);
    Disposable d2 = movieRepository.getVideoKeys(id).subscribe(videoLiveData::postValue);
    disposable.add(d1);
    disposable.add(d2);
  }

  public MutableLiveData<MovieDetail> getDetailLiveData() {
    return detailLiveData;
  }

  public MutableLiveData<List<String>> getVideoLiveData() {
    return videoLiveData;
  }

  public void addFavorite(Movie movie) {
    movie.setIsFavorite(1);
    movieRepository.saveMovie(movie).subscribe(new MovieCompletableObserver(movie));
  }

  public void removeFavorite(Movie movie) {
    movie.setIsFavorite(0);
    movieRepository.saveMovie(movie).subscribe(new MovieCompletableObserver(movie));
  }

  private class MovieCompletableObserver implements CompletableObserver {
    private Movie movie;

    MovieCompletableObserver(Movie movie) {
      this.movie = movie;
    }

    @Override public void onSubscribe(@NonNull Disposable d) {
      disposable.add(d);
    }

    @Override public void onComplete() {
      movieSubject.onNext(movie);
    }

    @Override public void onError(@NonNull Throwable e) {
      Timber.e(e);
    }
  }
}
