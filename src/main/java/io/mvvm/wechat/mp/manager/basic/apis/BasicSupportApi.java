package io.mvvm.wechat.mp.manager.basic.apis;

import io.mvvm.wechat.mp.infra.GsonWrapper;
import io.mvvm.wechat.mp.manager.apis.AbstractBaseApi;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: wechat-mp
 * @description: 获取Access token
 * <p>
 * see: <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">官方文档</a>
 * @author: Pan
 * @create: 2022-07-14 20:26
 **/
@Slf4j
public class BasicSupportApi extends AbstractBaseApi {

    protected GsonWrapper requestAccessToken(String appId, String secret) {
        return requestWrapper(() -> get("/cgi-bin/token")
                .setAppId(appId)
                .addParam("grant_type", "client_credential")
                .addParam("appid", appId)
                .addParam("secret", secret));
    }

}
