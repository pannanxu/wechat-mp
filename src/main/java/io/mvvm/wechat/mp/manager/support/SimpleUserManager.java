package io.mvvm.wechat.mp.manager.support;

import io.mvvm.wechat.mp.infra.Gsons;
import io.mvvm.wechat.mp.manager.IUserManager;
import io.mvvm.wechat.mp.manager.apis.UserApi;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-15 21:20
 **/
@Slf4j
public class SimpleUserManager extends UserApi implements IUserManager {

    private final IAccessTokenManager accessTokenManager;

    public SimpleUserManager(IAccessTokenManager accessTokenManager) {
        this.accessTokenManager = accessTokenManager;
    }

    @Override
    public String createTag(String appId, String tagName) {
        String accessToken = accessTokenManager.getAccessToken(appId);
        Gsons.Helper helper = requestCreateTag(accessToken, tagName);
        return helper.getAsString("tag", "id");
    }
}
