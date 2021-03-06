package io.mvvm.wechat.mp.manager.apis;

import com.google.gson.JsonObject;
import io.mvvm.wechat.mp.infra.GsonWrapper;

/**
 * @program: wechat-mp
 * @description: 用户管理Api
 *
 * <a href="https://developers.weixin.qq.com/doc/offiaccount/User_Management/User_Tag_Management.html">官方文档</a>
 * @author: Pan
 * @create: 2022-07-15 21:20
 **/
public class UserApi extends BaseApi {

    protected GsonWrapper requestCreateTag(String appId, String tagName) {
        String accessToken = getAccessToken(appId);

        JsonObject object = new JsonObject();
        JsonObject tag = new JsonObject();
        tag.addProperty("name", tagName);
        object.add("tag", tag);
        return requestWrapper(() -> post("/cgi-bin/tags/create", object.toString())
                .setAppId(appId)
                .addParam("access_token", accessToken));
    }

}
