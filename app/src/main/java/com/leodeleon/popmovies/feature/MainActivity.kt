package com.leodeleon.popmovies.feature

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.leodeleon.popmovies.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
  lateinit var host: NavHostFragment
  private val controller: NavController by lazy {
    Navigation.findNavController(this, R.id.nav_host)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val host = nav_host as NavHostFragment
    NavigationUI.setupWithNavController(bottom_bar, host.navController)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val result = super.onOptionsItemSelected(item)
    return NavigationUI.onNavDestinationSelected(item, controller) || result
  }

  override fun onSupportNavigateUp(): Boolean {
    return controller.navigateUp()
  }
}
