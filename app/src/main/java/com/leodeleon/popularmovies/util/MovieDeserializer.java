package com.leodeleon.popularmovies.util;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.leodeleon.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by leodeleon on 08/02/2017.
 */



public class MovieDeserializer implements JsonDeserializer<List<Movie>> {
  @Override
  public List<Movie> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Gson gson = new Gson();
    List<Movie> result = new ArrayList<>();

    JsonObject object = json.getAsJsonObject();
    return gson.fromJson(object.get("results"), typeOfT);
  }
}
