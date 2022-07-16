package io.mvvm.wechat.mp.manager.basic.support;

import io.mvvm.wechat.mp.infra.IConfig;
import io.mvvm.wechat.mp.infra.IConfigManager;
import io.mvvm.wechat.mp.manager.basic.apis.BasicSupportApi;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-14 20:42
 **/
@Slf4j
public class SimpleAccessTokenManager extends BasicSupportApi implements IAccessTokenManager {

    private final IConfigManager configManager;

    public SimpleAccessTokenManager(IConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public String getAccessToken(String appId) {
        IConfig config = configManager.getConfig(appId);
        return requestAccessToken(config.getAppId(), config.getSecret()).getAsString("access_token");
    }

}
