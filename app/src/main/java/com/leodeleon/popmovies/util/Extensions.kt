package com.leodeleon.popmovies.util

import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
  val snack = Snackbar.make(this, message, length)
  snack.f()
  snack.show()
}

fun bindString(@StringRes stringRes: Int) = object : ReadOnlyProperty<Context, String> {
  private var cached: String? = null

  override fun getValue(thisRef: Context, property: KProperty<*>): String
      = cached ?: thisRef.getString(stringRes).also { cached = it }
}
