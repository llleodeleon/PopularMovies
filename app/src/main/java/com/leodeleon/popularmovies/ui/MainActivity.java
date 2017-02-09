package com.leodeleon.popularmovies.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.leodeleon.popularmovies.R;
import com.leodeleon.popularmovies.api.API;
import com.leodeleon.popularmovies.api.MovieCalls;
import com.leodeleon.popularmovies.interfaces.MovieCallback;
import com.leodeleon.popularmovies.interfaces.MoviesCallback;
import com.leodeleon.popularmovies.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

  TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      mTextView = (TextView) findViewById(R.id.textview);


        MovieCalls.getInstance().getTopRatedMovies(new MoviesCallback() {
            @Override
            public void callback(List<Movie> movies) {
                mTextView.setText(movies.get(0).getTitle());

            }
        });

    }
}
