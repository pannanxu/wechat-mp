package io.mvvm.wechat.mp.manager.apis;

import com.google.common.base.Strings;
import io.mvvm.wechat.mp.infra.*;
import io.mvvm.wechat.mp.infra.constant.ApiResponseConstant;
import io.mvvm.wechat.mp.infra.http.BaseHttp;
import io.mvvm.wechat.mp.infra.http.Gets;
import io.mvvm.wechat.mp.infra.http.Posts;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-14 22:03
 **/
@Slf4j
public abstract class AbstractBaseApi {

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
     * 将请求包装一层, 提供了重试、刷新AccessToken 等功能
     */
    protected <T> GsonWrapper requestWrapper(Supplier<BaseHttp<T, ?>> supplier) {
        BaseHttp<T, ?> http = supplier.get();
        return http.getStringRetryHelper(response -> {
            GsonWrapper wrapper = wrapper(response);
            if (wrapper.isRefreshAccessToken()) {
                refreshAccessTokenApi(http.getAppId());
            }
            return http.buildMoreRetryParam(wrapper.isRetry(), wrapper);
        }, () -> {
            throw new RetryHelperException(Strings.lenientFormat("重试多次接口后失败 [%s]", http.getUri()));
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
     * 执行 AccessToken 刷新逻辑
     */
    protected void refreshAccessTokenApi(String appId) {
    }

}
