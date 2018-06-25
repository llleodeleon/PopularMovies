package com.leodeleon.popmovies.feature

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.feature.view.MoviePagerFragment


class MainActivity : AppCompatActivity() {

  private val mainFragment: MoviePagerFragment = MoviePagerFragment()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction()
        .add(R.id.container, mainFragment)
        .commit()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        onBackPressed()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    if (supportFragmentManager.backStackEntryCount == 0) {
      super.onBackPressed()
    } else {
      supportFragmentManager.popBackStackImmediate()
    }
  }

  fun addFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        .add(R.id.container, fragment)
        .addToBackStack(fragment.toString())
        .commit()
  }
}
