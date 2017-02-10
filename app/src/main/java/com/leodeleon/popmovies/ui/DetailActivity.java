package com.leodeleon.popmovies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.adapters.TrailerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {

  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;

  Unbinder unbinder;
  TrailerAdapter trailerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    unbinder = ButterKnife.bind(this);
    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setRecyclerView();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        supportFinishAfterTransition();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setRecyclerView() {
    trailerAdapter = new TrailerAdapter(this);
    mRecyclerView.setAdapter(trailerAdapter);
  }
}
