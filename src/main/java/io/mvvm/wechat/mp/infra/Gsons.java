package io.mvvm.wechat.mp.infra;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import lombok.Setter;

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

    public static Helper wrapper(JsonObject json) {
        return new Helper(json);
    }

    public static class Helper {

        private final JsonObject json;

        @Setter
        @Getter
        private boolean retry;
        @Setter
        @Getter
        private boolean refreshAccessToken;
        @Setter
        @Getter
        private boolean result;
        @Setter
        @Getter
        private String  errMsg;
        @Setter
        @Getter
        private String  errCode;

        public Helper(JsonObject json) {
            if (json == null) {
                json = new JsonObject();
            }
            this.json = json;
        }

        public String getAsString(String key) {
            return json.has(key) ? json.get(key).getAsString() : "";
        }

        public int getAsInt(String key) {
            json.getAsJsonArray("");
            return json.has(key) ? json.get(key).getAsInt() : 0;
        }

        public JsonObject getAsJsonObject(String key) {
            return json.get(key).getAsJsonObject();
        }

        public List<String> getAsJsonArrayString(String key) {
            List<String> list = Lists.newArrayList();
            if (!json.has(key)) {
                return list;
            }
            JsonArray array = json.getAsJsonArray(key);
            for (JsonElement el : array) {
                list.add(el.getAsString());
            }
            return list;
        }

        public String getAsString(String... args) {
            JsonObject object = json;
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (object.has(arg)) {
                    if (i == args.length - 1) {
                        return object.get(arg).getAsString();
                    }
                    object = object.getAsJsonObject(arg);
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.json.toString();
        }
    }

    public static void main(String[] args) {
        Helper wrapper = Gsons.wrapper(Gsons.parse("{\"tag\":{\"id\":100,\"name\":\"张三\"}}"));
        String id = wrapper.getAsString("tag", "id");
        System.out.println(id);
    }
}
