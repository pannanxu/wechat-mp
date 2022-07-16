package io.mvvm.wechat.mp.infra;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * @author: Pan
 **/
public final class Gsons {

    private static final Gson gson;

    static {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    private Gsons() {
    }

    public static <T> String toJsonString(T t) {
        if (null == t) {
            return null;
        }
        return gson.toJson(t);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        if (null == json || "".equals(json)) {
            return null;
        }
        return gson.fromJson(json, clazz);
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> key, Class<V> value) {
        if (null == json || "".equals(json)) {
            return null;
        }
        return gson.fromJson(json, new TypeToken<Map<K, V>>() {
        }.getType());
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    public static JsonObject parse(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    public static JsonArray parseArray(String json) {
        return JsonParser.parseString(json).getAsJsonArray();
    }

    public static JsonObject parse(Reader json) {
        return JsonParser.parseReader(json).getAsJsonObject();
    }

    public static JsonObject parse(JsonReader json) {
        return JsonParser.parseReader(json).getAsJsonObject();
    }

}
