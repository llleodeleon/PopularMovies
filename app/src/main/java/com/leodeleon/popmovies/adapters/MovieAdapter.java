package com.leodeleon.popmovies.adapters;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.feature.DetailsActivity;
import com.leodeleon.popmovies.feature.MainActivity;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.util.GlideHelper;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leodeleon on 09/02/2017.
 */

public class MovieAdapter extends LoaderAdapter {

  private List<Movie> movies = new ArrayList<>();
  private CompositeDisposable disposables = new CompositeDisposable();

  @Override protected RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_view, parent, false);
    return new MovieViewHolder(view);
  }

  @Override protected void onBindContentItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((MovieViewHolder) holder).bindView(movies.get(position));
  }

  @Override protected int getContentItemId(int position) {
    return movies.get(position).getId();
  }

  @Override protected int getContentItemCount() {
    return movies.size();
  }

  @Override public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
    super.onDetachedFromRecyclerView(recyclerView);
    disposables.clear();
  }

  public void setMovies(List<Movie> newMovies) {
    movies.addAll(newMovies);
    stopLoading();
    notifyItemRangeChanged(movies.size(), newMovies.size(), newMovies);
  }

  class MovieViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cardview) CardView mCardView;
    @BindView(R.id.poster) ImageView mPosterImage;
    private Movie movie;

    public MovieViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    public void bindView(Movie movie) {
      this.movie = movie;
      GlideHelper.loadPoster(itemView.getContext(), movie.getPosterPath(), mPosterImage);
      Disposable d1 = RxView.clicks(mCardView).subscribe(o -> goToDetailActivity());
      disposables.add(d1);
    }

    private void goToDetailActivity() {
      MainActivity activity = (MainActivity) itemView.getContext();
      Intent intent = new Intent(activity, DetailsActivity.class);
      intent.putExtra(DetailsActivity.MOVIE, new Gson().toJson(movie));
      ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, mCardView, "cardview");
      activity.startActivity(intent, options.toBundle());
    }
  }
}
