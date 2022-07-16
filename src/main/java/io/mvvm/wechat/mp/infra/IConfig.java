package io.mvvm.wechat.mp.infra;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-14 20:31
 **/
public interface IConfig {

    String getAppId();

    String getSecret();

    static IConfig of(String appId, String secret) {
        return new SimpleConfig(appId, secret);
    }
}
