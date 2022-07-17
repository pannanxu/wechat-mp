package io.mvvm.wechat.mp.manager;

import com.google.common.base.Verify;
import com.google.common.reflect.MutableTypeToInstanceMap;
import io.mvvm.wechat.mp.infra.IConfigManager;
import io.mvvm.wechat.mp.infra.SimpleConfigManager;
import io.mvvm.wechat.mp.infra.WechatException;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import io.mvvm.wechat.mp.manager.basic.support.GuavaCacheAccessTokenManager;
import io.mvvm.wechat.mp.manager.support.SimpleApiDomainManager;
import io.mvvm.wechat.mp.manager.support.SimpleMaterialManager;
import io.mvvm.wechat.mp.manager.support.SimpleUserManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
        API instance = container.getInstance(impl);
        if (null == instance) {
            synchronized (SimpleWechatMpApi.class) {
                instance = container.getInstance(api);
                if (null == instance) {
                    instance = newInstance(impl);
                    container.putInstance(api, instance);
                }
            }
        }
        Verify.verifyNotNull(instance);
        return instance;
    }

    /**
     * 创建对象实例
     * <p>
     * 在有多个构造方法时，默认采用第一个构造器
     * <p>
     * 构造器所需要的对象在 bean 容器中被管理的情况下会自动将对象注入
     *
     * @param clazz 需要创建对象的class
     * @return 对象的实例
     */
    @SuppressWarnings("unchecked")
    private <T> T newInstance(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        T instance = null;

        if (constructors.length == 0) {
            try {
                instance = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new WechatException("创建Bean异常", e);
            }
        } else {
            Constructor<?> constructor = constructors[0]; // 多个构造方法时默认取第一个去创建对象
            Object[] args = getParameterValues(constructor);
            try {
                instance = (T) constructor.newInstance(args);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new WechatException("创建Bean异常", e);
            }
        }

        Verify.verifyNotNull(instance);
        return instance;
    }

    private Object[] getParameterValues(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = container.getInstance(parameterTypes[i]);
        }
        return args;
    }
}
