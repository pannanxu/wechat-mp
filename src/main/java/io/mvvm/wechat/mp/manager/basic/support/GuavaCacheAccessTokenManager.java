package io.mvvm.wechat.mp.manager.basic.support;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.mvvm.wechat.mp.infra.IConfigManager;
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
            .build(CacheLoader.from(this::refreshAccessToken));

    public GuavaCacheAccessTokenManager(IConfigManager configManager) {
        super(configManager);
    }

    @Override
    public String getAccessToken(String appId) {
        return cache.getUnchecked(appId);
    }

    public String refreshAccessToken(String key) {
        log.info("Ready to refresh the accessToken of {}.", key);
        return super.getAccessToken(key);
    }

}
