package com.github.jupittar.commlib.utilities;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class GsonUtils {

  private static Gson gson = new Gson();

  /**
   * Convert a json String to a java Object
   */
  public static <T> T toObject(String jsonString, Class<T> typeofT) {
    return gson.fromJson(jsonString, typeofT);
  }

  /**
   * Convert a json String to a java List
   */
  public static <T> List<T> toList(String jsonString, Type typeOfT) {
    return gson.fromJson(jsonString, typeOfT);
  }

  /**
   * Convert a json String to a java Map
   */
  public static <K, V> Map<K, V> toMap(String jsonString, Type typeOfT) {
    return gson.fromJson(jsonString, typeOfT);
  }

  /**
   * Convert a java Object to a json String
   */
  public static String toJson(Object obj) {
    return gson.toJson(obj);
  }
}
