package io.mvvm.wechat.mp.infra;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.Optional;

/**
 * @program: wechat-mp
 * @description: 反射工具包
 * @author: Pan
 * @create: 2022-07-18 14:40
 **/
@Slf4j
public class Reflections {

    /**
     * 创建对象实例
     *
     * @param constructorArgsValue 构造器参数列表
     * @return optional instance
     */
    public static <T> Optional<T> newInstance(Class<T> clazz, Object... constructorArgsValue) {
        T instance = null;
        try {
            Constructor<T> constructor = clazz.getConstructor(getParameterTypes(clazz, constructorArgsValue.length));
            instance = constructor.newInstance(constructorArgsValue);
        } catch (Exception e) {
            log.error("Failed to create {} instance.", clazz, e);
        }
        return Optional.ofNullable(instance);
    }

    /**
     * 根据长度获取类中的构造方法
     *
     * @param argCount 参数长度
     */
    public static <T> Class<?>[] getParameterTypes(Class<T> clazz, int argCount) {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == argCount) {
                return constructor.getParameterTypes();
            }
        }
        throw new RuntimeException("暂无可用构造器");
    }

    /**
     * 获取类的第一个构造方法的参数类型
     */
    public static <T> Class<?>[] getDefaultParameterTypes(Class<T> clazz) {
        return getDefaultConstructor(clazz).getParameterTypes();
    }

    public static <T> Constructor<?> getDefaultConstructor(Class<T> clazz) {
        return clazz.getDeclaredConstructors()[0];
    }

}
