package io.mvvm.wechat.mp.infra;

import com.google.common.base.Function;

/**
 * @program: wechat-mp
 * @description: 缓存管理器
 * @author: Pan
 * @create: 2022-07-14 23:18
 **/
public interface ICacheManager {

    String getAccessToken(String appId);

    String getCache(String key);

    void setCache(String key, String value);

    void registerRefresh(String key, Function<String, String> fn);

}
