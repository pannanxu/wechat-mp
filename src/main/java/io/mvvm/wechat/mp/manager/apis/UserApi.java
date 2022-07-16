package io.mvvm.wechat.mp.manager.apis;

import com.google.gson.JsonObject;
import io.mvvm.wechat.mp.infra.Gsons;

/**
 * @program: wechat-mp
 * @description: 用户管理Api
 *
 * <a href="https://developers.weixin.qq.com/doc/offiaccount/User_Management/User_Tag_Management.html">官方文档</a>
 * @author: Pan
 * @create: 2022-07-15 21:20
 **/
public class UserApi extends BaseApi {

    protected Gsons.Helper requestCreateTag(String accessToken, String tagName) {
        JsonObject object = new JsonObject();
        JsonObject tag = new JsonObject();
        tag.addProperty("name", tagName);
        object.add("tag", tag);
        String response = post("/cgi-bin/tags/create?access_token=" + accessToken, object.toString()).getString();
        return wrapper(response);
    }

}
