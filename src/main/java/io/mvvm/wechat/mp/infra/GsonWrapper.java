package io.mvvm.wechat.mp.infra;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    public JsonObject getJson() {
        return json;
    }

    public String getAsStringOrDefault(String key, String defVal) {
        return json.has(key) ? json.get(key).getAsString() : defVal;
    }

    public String getAsString(String key) {
        return getAsStringOrDefault(key, "");
    }

    public List<String> getAsJsonArrayString(String key) {
        List<String> list = Lists.newArrayList();
        if (json.has(key)) {
            JsonArray array = json.getAsJsonArray(key);
            for (JsonElement el : array) {
                list.add(el.getAsString());
            }
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
