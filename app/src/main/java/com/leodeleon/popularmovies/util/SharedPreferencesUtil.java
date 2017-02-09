package com.leodeleon.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by leodeleon on 09/02/2017.
 */

public class SharedPreferencesUtil {
  private final String file = "global_preference";

  private static SharedPreferencesUtil instance;
  private Context context;

  public static void initialize(Context context) {
    instance = new SharedPreferencesUtil();
    instance.context = context;
  }

  public static SharedPreferencesUtil getInstance() {
    return instance;
  }

  public void putInt(String key, int value) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putInt(key, value);
    editor.apply();
  }

  public void putBoolean(String key, boolean value) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  public void putString(String key, String value) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString(key, value);
    editor.apply();
  }

  public int getInt(String key) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    return sharedPref.getInt(key, 0);
  }

  public boolean getBoolean(String key) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    return sharedPref.getBoolean(key, false);
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    return sharedPref.getBoolean(key, defaultValue);
  }

  public String getString(String key) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    return sharedPref.getString(key, null);
  }
}
