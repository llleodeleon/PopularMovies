package com.leodeleon.popmovies.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by leodeleon on 11/02/2017.
 */

public class LockableViewPager extends ViewPager {

  private boolean isPagingEnabled = false;

  private View mCurrentView;


  public LockableViewPager(Context context) {
    super(context);
  }

  public LockableViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return this.isPagingEnabled && super.onTouchEvent(event);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return this.isPagingEnabled && super.onInterceptTouchEvent(event);
  }

  public void setPagingEnabled(boolean b) {
    this.isPagingEnabled = b;
  }

  public void measureCurrentView(View currentView) {
    mCurrentView = currentView;
    requestLayout();
  }
}