package io.mvvm.wechat.mp.manager.apis;

import io.mvvm.wechat.mp.infra.WechatException;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import lombok.Setter;

/**
 * @description: 继承此类可以直接获取到 accessToken
 * <p>
 * 在 SimpleWechatMpApi 实例中, 会通过反射自动给 accessTokenManager 注入实例
 * @author: Pan
 **/
public class BaseApi extends AbstractBaseApi {

    @Setter
    private IAccessTokenManager accessTokenManager;

    @Override
    protected void refreshAccessTokenApi(String appId) {
        getAccessTokenManager().refreshAccessToken(appId);
    }

    protected String getAccessToken(String appId) {
        return getAccessTokenManager().getAccessToken(appId);
    }

    public IAccessTokenManager getAccessTokenManager() {
        if (null == accessTokenManager) {
            throw new WechatException("accessTokenManager is null.");
        }
        return accessTokenManager;
    }

}
