package io.mvvm.wechat.mp.infra;

import io.mvvm.wechat.mp.manager.IBaseBean;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-15 22:25
 **/
public interface IConfigManager extends IBaseBean {


    IConfig getConfig(String appId);

    void setConfig(IConfig config);

}
