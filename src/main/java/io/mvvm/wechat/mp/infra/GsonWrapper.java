package io.mvvm.wechat.mp.infra;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

/**
 * @program: wechat-mp
 * @description: Json 包装器
 * @author: Pan
 * @create: 2022-07-16 21:00
 **/
public class GsonWrapper {
    /**
     * 是否进行重试请求
     */
    @Setter
    @Getter
    private       boolean    retry;
    /**
     * 是否刷新 AccessToken
     */
    @Setter
    @Getter
    private       boolean    refreshAccessToken;
    /**
     * 是否请求成功
     * <p>
     * 存在 errCode 时, 如果 errCode != “0” 则为 false
     */
    @Setter
    @Getter
    private       boolean    result;
    /**
     * 请求 api 返回的 errMsg
     * <p>
     * 可能会为空
     */
    @Setter
    @Getter
    private       String     errMsg;
    /**
     * 请求 api 返回的 errCode
     * <p>
     * 可能会为空, 为空无法根据此 code 来匹配统一处理异常
     * <p>
     * 需要每个接口自行处理结果
     */
    @Setter
    @Getter
    private       String     errCode;
    /**
     * 响应体, JSON
     */
    private final JsonObject json;

    public static GsonWrapper of(String json) {
        return new GsonWrapper(Gsons.parse(json));
    }

    public static GsonWrapper of(JsonObject json) {
        return new GsonWrapper(json);
    }

    public GsonWrapper(JsonObject json) {
        this.json = json;
    }

    public Optional<JsonObject> getJson() {
        return Optional.ofNullable(this.json);
    }

    public String getAsString(String key, String defVal) {
        return getJson().map(json -> json.get(key)).map(JsonElement::getAsString).orElse(defVal);
    }

    public String getAsString(JsonObject json, String key, String defVal) {
        return Optional.ofNullable(json.get(key)).map(JsonElement::getAsString).orElse(defVal);
    }

    public String getAsString(String key) {
        return getAsString(key, null);
    }

    public List<String> getAsJsonArrayString(String key) {
        return getJson().map(json -> json.get(key))
                .map(JsonElement::getAsJsonArray)
                .flatMap((Function<JsonArray, Optional<List<String>>>) array ->
                        Optional.of(stringElementArrayToList(array)))
                .orElse(Lists.newArrayList());
    }

    public String getAsStringArgs(String... args) {
        return getJson().map(json -> {
            JsonObject object = json;
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (object.has(arg)) {
                    if (i == args.length - 1) {
                        return getAsString(object, arg, null);
                    }
                    object = object.getAsJsonObject(arg);
                }
            }
            return null;
        }).orElse(null);
    }

    public GsonWrapper getAsWrapperArgs(String... args) {
        return getJson().map(json -> {
                    JsonObject object = json;
                    for (String arg : args) {
                        if (object.has(arg)) {
                            object = object.getAsJsonObject(arg);
                        }
                    }
                    return object;
                })
                .map(GsonWrapper::of)
                .orElse(new GsonWrapper(null));
    }

    @Override
    public String toString() {
        return this.json.toString();
    }

    private List<String> stringElementArrayToList(JsonArray array) {
        List<String> list = Lists.newArrayList();
        for (JsonElement el : array) {
            list.add(el.getAsString());
        }
        return list;
    }
}
