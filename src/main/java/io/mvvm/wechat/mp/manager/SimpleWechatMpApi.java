package io.mvvm.wechat.mp.manager;

import com.google.common.reflect.MutableTypeToInstanceMap;
import io.mvvm.wechat.mp.infra.Reflections;
import io.mvvm.wechat.mp.infra.WechatException;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import io.mvvm.wechat.mp.manager.basic.support.GuavaCacheAccessTokenManager;
import io.mvvm.wechat.mp.manager.support.SimpleApiDomainManager;
import io.mvvm.wechat.mp.manager.support.SimpleConfigManager;
import io.mvvm.wechat.mp.manager.support.SimpleMaterialManager;
import io.mvvm.wechat.mp.manager.support.SimpleUserManager;

/**
 * @program: wechat-mp
 * @description: 简单的 Ioc 注入管理。需手动处理注入顺序，否则会找不到依赖
 * @author: Pan
 * @create: 2022-07-14 21:35
 **/
public class SimpleWechatMpApi implements IWechatMpApi {

    protected final MutableTypeToInstanceMap<Object> container = new MutableTypeToInstanceMap<>();

    public SimpleWechatMpApi() {
        newConfigManager();
        newAccessTokenManger();
        newApiDomainManager();
    }

    protected void newConfigManager() {
        lazyNewInstance(IConfigManager.class, SimpleConfigManager.class);
    }

    protected void newAccessTokenManger() {
        lazyNewInstance(IAccessTokenManager.class, GuavaCacheAccessTokenManager.class);
    }

    protected void newApiDomainManager() {
        lazyNewInstance(IApiDomainManager.class, SimpleApiDomainManager.class);
    }

    @Override
    public IConfigManager getConfigManager() {
        return container.getInstance(IConfigManager.class);
    }

    @Override
    public IAccessTokenManager getAccessTokenManager() {
        return container.getInstance(IAccessTokenManager.class);
    }

    @Override
    public IApiDomainManager getApiDomainManager() {
        return container.getInstance(IApiDomainManager.class);
    }

    @Override
    public IUserManager getUserManager() {
        return lazyNewInstance(IUserManager.class, SimpleUserManager.class);
    }

    @Override
    public IMaterialManager getMaterialManager() {
        return lazyNewInstance(IMaterialManager.class, SimpleMaterialManager.class);
    }

    /**
     * 懒加载创建实例
     *
     * @param api  接口
     * @param impl 接口的实现
     * @return instance
     */
    protected <API, IMPL extends API> API lazyNewInstance(Class<API> api, Class<IMPL> impl) {
        API instance = container.getInstance(api);
        if (null == instance) {
            synchronized (SimpleWechatMpApi.class) {
                instance = container.getInstance(api);
                if (null == instance) {
                    instance = newInstance(impl);
                    container.putInstance(api, instance);
                }
            }
        }
        return instance;
    }

    private <T> T newInstance(Class<T> clazz) {
        Object[] parameterValues = getParameterValues(clazz);
        return Reflections.newInstance(clazz, parameterValues)
                .orElseThrow(() -> new WechatException("Failed to create %s instance.", clazz));
    }

    private <T> Object[] getParameterValues(Class<T> clazz) {
        Class<?>[] parameterTypes = Reflections.getDefaultParameterTypes(clazz);
        Object[] parameterValues = new Object[parameterTypes.length];
        for (int i = 0; i < parameterValues.length; i++) {
            parameterValues[i] = container.getInstance(parameterTypes[i]);
        }
        return parameterValues;
    }
}
