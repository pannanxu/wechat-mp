package io.mvvm.wechat.mp.manager;

import io.mvvm.wechat.mp.infra.IConfig;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-15 22:25
 **/
public interface IConfigManager {


    IConfig getConfig(String appId);

    void setConfig(IConfig config);

}
