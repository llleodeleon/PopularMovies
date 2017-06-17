package com.leodeleon.popmovies.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.Locale

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun String.formatText(text: String, value: Int): String {
  return String.format(Locale.getDefault(), text, value)
}