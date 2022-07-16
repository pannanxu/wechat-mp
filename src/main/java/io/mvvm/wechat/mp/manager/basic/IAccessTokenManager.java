package io.mvvm.wechat.mp.manager.basic;

import io.mvvm.wechat.mp.manager.IBaseBean;

/**
 * @program: wechat-mp
 * @description: 获取 AccessToken
 * @author: Pan
 * @create: 2022-07-14 20:59
 **/
public interface IAccessTokenManager extends IBaseBean {
    /**
     * 获取 AccessToken
     *
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">官方文档</a>
     *
     * @return AccessToken
     */
    default String getAccessToken(String appId) {
        return null;
    };

    default String refreshAccessToken(String appId) {
        return null;
    };
}
