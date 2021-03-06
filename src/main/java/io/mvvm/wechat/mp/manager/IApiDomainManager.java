package io.mvvm.wechat.mp.manager;

import java.util.List;

/**
 * @program: wechat-mp
 * @description: 基础支持
 * @author: Pan
 * @create: 2022-07-14 20:59
 **/
public interface IApiDomainManager {

    /**
     * 获取微信服务器 IP 地址
     */
    List<String> getApiDomainIp(String appId);

}
