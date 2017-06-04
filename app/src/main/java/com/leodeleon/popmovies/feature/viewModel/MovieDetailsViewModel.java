package com.leodeleon.popmovies.feature.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.leodeleon.popmovies.data.MovieRepository;
import com.leodeleon.popmovies.model.MovieDetail;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;

public class MovieDetailsViewModel extends ViewModel {


  private MutableLiveData<MovieDetail> detailLiveData = new MutableLiveData<>();
  private MutableLiveData<List<String>> videoLiveData = new MutableLiveData<>();
  private MovieRepository movieRepository;
  private CompositeDisposable disposable = new CompositeDisposable();

  @Inject
  public MovieDetailsViewModel(MovieRepository repository) {
    movieRepository = repository;
  }

  public void loadDetails(int id) {
    Disposable d1 = movieRepository.getMovieDetail(id).subscribe(movieDetail -> detailLiveData.postValue(movieDetail));
    Disposable d2 = movieRepository.getVideoKeys(id).subscribe(keys -> videoLiveData.postValue(keys));
    disposable.add(d1);
    disposable.add(d2);
  }

  public MutableLiveData<MovieDetail> getDetailLiveData() {
    return detailLiveData;
  }

  public MutableLiveData<List<String>> getVideoLiveData() {
    return videoLiveData;
  }

  @Override protected void onCleared() {
    super.onCleared();
    disposable.clear();
  }
}
