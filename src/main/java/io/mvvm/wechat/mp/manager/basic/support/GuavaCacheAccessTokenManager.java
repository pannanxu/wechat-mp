package io.mvvm.wechat.mp.manager.basic.support;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.mvvm.wechat.mp.manager.IConfigManager;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @program: wechat-mp
 * @description: accessToken 缓存管理器
 * @author: Pan
 * @create: 2022-07-14 21:00
 **/
@Slf4j
public class GuavaCacheAccessTokenManager extends SimpleAccessTokenManager implements IAccessTokenManager {

    private final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite((int) (7200 / 1.2), TimeUnit.SECONDS)
            .build(CacheLoader.from(this::refresh));

    public GuavaCacheAccessTokenManager(IConfigManager configManager) {
        super(configManager);
    }

    @Override
    public String getAccessToken(String appId) {
        return cache.getUnchecked(appId);
    }

    @Override
    public String refreshAccessToken(String appId) {
        cache.refresh(appId);
        return getAccessToken(appId);
    }

    public String refresh(String appId) {
        log.debug("Ready to refresh the accessToken of {}.", appId);
        return super.getAccessToken(appId);
    }

}
