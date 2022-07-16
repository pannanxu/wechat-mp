package io.mvvm.wechat.mp.infra;

import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.mvvm.wechat.mp.infra.ICacheManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: wechat-mp
 * @description: Guava 缓存
 * @author: Pan
 * @create: 2022-07-14 23:19
 **/
public class GuavaCacheManager implements ICacheManager {

    private static final Map<String, Function<String, String>> map = new ConcurrentHashMap<>();

    private final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(23, TimeUnit.HOURS)
            .build(CacheLoader.from(this::apply));

    private String apply(String key) {
        return map.get(key).apply(key);
    }

    @Override
    public String getAccessToken(String appId) {
        return getCache("accessToken");
    }

    @Override
    public String getCache(String key) {
        return cache.getUnchecked(key);
    }

    @Override
    public void setCache(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public void registerRefresh(String key, Function<String, String> fn) {
        map.put(key, fn);
    }
}
