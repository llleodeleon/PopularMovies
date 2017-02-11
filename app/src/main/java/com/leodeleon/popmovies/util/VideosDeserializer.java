package com.leodeleon.popmovies.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.leodeleon.popmovies.model.MovieDetail;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leodeleon on 08/02/2017.
 */



public class VideosDeserializer implements JsonDeserializer<ArrayList<String>> {
  @Override
  public ArrayList<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Gson gson = new Gson();
    ArrayList<MovieDetail> result = new ArrayList<>();

    JsonObject object = json.getAsJsonObject();
    return gson.fromJson(object.get("results"), typeOfT);
  }
}
