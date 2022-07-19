package io.mvvm.wechat.mp.manager.apis;

import io.mvvm.wechat.mp.infra.GsonWrapper;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-16 16:10
 **/
public class ApiDomainApi extends BaseApi {

    // 获取微信服务器IP地址
    public GsonWrapper requestGetApiDomainIp(String appId) {
        String accessToken = getAccessToken(appId);
        return requestWrapper(() -> get("/cgi-bin/get_api_domain_ip")
                .setAppId(appId)
                .addParam("access_token", accessToken));
    }

}
