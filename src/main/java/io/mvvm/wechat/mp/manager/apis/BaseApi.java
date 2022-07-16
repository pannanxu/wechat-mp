package io.mvvm.wechat.mp.manager.apis;

import com.google.common.base.Strings;
import io.mvvm.wechat.mp.infra.Gsons;
import io.mvvm.wechat.mp.infra.constant.ApiResponseConstant;
import io.mvvm.wechat.mp.infra.http.Gets;
import io.mvvm.wechat.mp.infra.http.Posts;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-14 22:03
 **/
@Slf4j
public class BaseApi {

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

    protected Gsons.Helper wrapper(String json) {
        Gsons.Helper helper = Gsons.wrapper(Gsons.parse(json));
        String errCode = helper.getAsString(ApiResponseConstant.errCode);
        String errMsg = helper.getAsString(ApiResponseConstant.errMsg);
        helper.setResult(true);
        helper.setErrMsg(errMsg);
        if (!Strings.isNullOrEmpty(errCode) && !"0".equals(errCode)) {
            log.error("Failed to request api. code is '{}'. msg is '{}'", errCode, errMsg);
            helper.setResult(false);
        }
        return helper;
    }

}
