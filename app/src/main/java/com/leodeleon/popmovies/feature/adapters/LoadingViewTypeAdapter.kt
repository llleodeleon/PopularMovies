package com.leodeleon.popmovies.feature.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import com.leodeleon.popmovies.R
import com.leodeleon.popmovies.feature.common.ViewType
import com.leodeleon.popmovies.feature.common.ViewTypeAdapter
import com.leodeleon.popmovies.util.inflate

class LoadingViewTypeAdapter : ViewTypeAdapter {

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
    return LoadingViewHolder(parent.inflate(R.layout.view_loader))
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
  }

  class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}