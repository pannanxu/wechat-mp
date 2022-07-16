package io.mvvm.wechat.mp.manager;

import io.mvvm.wechat.mp.infra.IConfigManager;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-15 22:44
 **/
public interface IWechatMpApi {

    IConfigManager getConfigManager();

    IAccessTokenManager getAccessTokenManager();

    IApiDomainManager getApiDomainManager();

    IUserManager getUserManager();

    IMaterialManager getMaterialManager();

}
