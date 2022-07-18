package io.mvvm.wechat.mp.manager.apis;

import com.google.common.base.Strings;
import io.mvvm.wechat.mp.infra.*;
import io.mvvm.wechat.mp.infra.constant.ApiResponseConstant;
import io.mvvm.wechat.mp.infra.http.BaseHttp;
import io.mvvm.wechat.mp.infra.http.Gets;
import io.mvvm.wechat.mp.infra.http.Posts;
import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-14 22:03
 **/
@Slf4j
public abstract class BaseApi {

    protected final String baseUrl = "https://api.weixin.qq.com";

    protected Gets get(String url) {
        return Gets.request(baseUrl + url);
    }

    protected Gets get(String url, final Map<String, String> params) {
        return Gets.request(baseUrl + url, params);
    }

    protected Posts post(String url) {
        return Posts.request(baseUrl + url);
    }

    protected Posts post(String url, String body) {
        return Posts.request(baseUrl + url, body.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将请求包装一层, 提供了重试、requestId、刷新AccessToken 等功能
     */
    protected <T> GsonWrapper requestWrapper(Supplier<BaseHttp<T>> supplier) {
        String requestId = UUID.randomUUID().toString();
        return retryHelper(() -> {
            BaseHttp<T> http = supplier.get();
            http.addHeader("requestId", requestId);
            GsonWrapper wrapper = wrapper(http.getString());
            refreshAccessToken(http, wrapper);
            return ValueWrappers.of(http, wrapper);
        });
    }

    /**
     * 包装响应的 json 方便操作
     */
    protected GsonWrapper wrapper(String json) {
        GsonWrapper helper = GsonWrapper.of(json);
        String errCode = helper.getAsString(ApiResponseConstant.errCode);
        String errMsg = helper.getAsString(ApiResponseConstant.errMsg);
        helper.setResult(!Strings.isNullOrEmpty(errCode) && !"0".equals(errCode));
        helper.setErrMsg(errMsg);
        helper.setErrCode(errCode);
        helper.setRetry(Globals.RETRY_CODES.contains(errCode));
        helper.setRefreshAccessToken(Globals.REFRESH_ACCESS_TOKEN_CODES.contains(errCode));

        if (helper.isResult()) {
            if (!helper.isRetry() || !helper.isRefreshAccessToken()) {
                throw new WechatException("异常错误: %s -> %s:%s", Globals.ReturnCode.getInstance(errCode).getDesc(), errCode, errMsg);
            }
        }
        return helper;
    }

    /**
     * 子类重写此方法后提供一个 AccessToken 管理器
     */
    protected IAccessTokenManager getAccessTokenManager() {
        throw new WechatException("不支持的刷新AccessToken");
    }

    /**
     * 执行 AccessToken 刷新逻辑
     */
    private <T> void refreshAccessToken(BaseHttp<T> http, GsonWrapper wrapper) {
        if (wrapper.isRefreshAccessToken()) {
            getAccessTokenManager().refreshAccessToken(http.getAppId());
        }
    }

    /**
     * 接口重试请求. 最大会进行3次的请求, 如果全部失败, 则会抛出异常
     */
    private <T> GsonWrapper retryHelper(Supplier<ValueWrappers.Value2<BaseHttp<T>, GsonWrapper>> supplier) {
        BaseHttp<T> http = null;
        int retryCount = 4;
        AtomicInteger inc = new AtomicInteger(0);
        do {
            int i = inc.incrementAndGet();
            ValueWrappers.Value2<BaseHttp<T>, GsonWrapper> value = supplier.get();
            http = value.getT1();
            GsonWrapper wrapper = value.getT2();
            if (!wrapper.isRetry()) {
                return wrapper;
            }
            if (i < retryCount) {
                log.debug("ready for {}st retry.", i);
                sleep(i);
            }
        } while (inc.get() < retryCount);
        throw new RetryHelperException(Strings.lenientFormat("执行 %s 次接口后失败 [%s]", inc.get(), http.getUri()));
    }

    private void sleep(int i) {
        try {
            Thread.sleep(500L * i);
        } catch (InterruptedException e) {
            throw new WechatException("重试异常");
        }
    }
}
