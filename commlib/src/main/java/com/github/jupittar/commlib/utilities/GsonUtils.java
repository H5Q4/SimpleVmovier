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

    public static <T> T toObject(String jsonString, Class<T> typeofT) {
        return gson.fromJson(jsonString, typeofT);
    }

    public static <T> List<T> toList(String jsonString, Type typeOfT){
        return gson.fromJson(jsonString, typeOfT);
    }

    public static <K, V> Map<K, V> toMap(String jsonString, Type typeOfT){
        return gson.fromJson(jsonString, typeOfT);
    }

    public static <T> List<T> toObjectList(String jsonString, Class<T> typeofT) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        List<T> result = new ArrayList<T>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            T a = toObject(jsonArray.get(i).toString(), typeofT);
            result.add(a);
        }
        return result;
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
