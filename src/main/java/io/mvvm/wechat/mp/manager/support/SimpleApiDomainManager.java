package io.mvvm.wechat.mp.manager.support;

import io.mvvm.wechat.mp.infra.Gsons;
import io.mvvm.wechat.mp.manager.apis.ApiDomainApi;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import io.mvvm.wechat.mp.manager.IApiDomainManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-14 20:42
 **/
@Slf4j
public class SimpleApiDomainManager extends ApiDomainApi implements IApiDomainManager {

    public SimpleApiDomainManager(IAccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public List<String> getApiDomainIp(String appId) {
        Gsons.Helper helper = requestGetApiDomainIp(appId);
        return helper.getAsJsonArrayString("ip_list");
    }

}
