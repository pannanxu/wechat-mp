package io.mvvm.wechat.mp.manager.basic.support;

import io.mvvm.wechat.mp.infra.Gsons;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import io.mvvm.wechat.mp.manager.basic.apis.BasicSupportApi;
import io.mvvm.wechat.mp.manager.basic.IApiDomainManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-14 20:42
 **/
@Slf4j
public class SimpleApiDomainManager extends BasicSupportApi implements IApiDomainManager {

    private final IAccessTokenManager accessTokenManager;

    public SimpleApiDomainManager(IAccessTokenManager accessTokenManager) {
        this.accessTokenManager = accessTokenManager;
    }

    @Override
    public List<String> getApiDomainIp(String appId) {
        String accessToken = accessTokenManager.getAccessToken(appId);
        Gsons.Helper helper = requestGetApiDomainIp(accessToken);
        return helper.getAsJsonArrayString("ip_list");
    }

}
