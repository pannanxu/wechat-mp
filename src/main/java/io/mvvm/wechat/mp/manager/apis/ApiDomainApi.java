package io.mvvm.wechat.mp.manager.apis;

import io.mvvm.wechat.mp.infra.Gsons;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import lombok.Getter;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-16 16:10
 **/
public class ApiDomainApi extends BaseApi {

    @Getter
    private final IAccessTokenManager accessTokenManager;

    public ApiDomainApi(IAccessTokenManager accessTokenManager) {
        this.accessTokenManager = accessTokenManager;
    }

    // 获取微信服务器IP地址
    public Gsons.Helper requestGetApiDomainIp(String appId) {
        String accessToken = accessTokenManager.getAccessToken(appId);
        return requestWrapper(() -> get("/cgi-bin/get_api_domain_ip")
                .addParam("access_token", accessToken));
    }

}
