package io.mvvm.wechat.mp.infra;

import com.google.common.base.Strings;

/**
 * @program: wechat-mp
 * @description: 通用异常
 * @author: Pan
 * @create: 2022-07-16 16:35
 **/
public class WechatException extends RuntimeException {
    public WechatException() {
    }

    public WechatException(String message) {
        super(message);
    }

    public WechatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatException(Throwable cause) {
        super(cause);
    }

    public WechatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WechatException(String message, Object... args) {
        super(Strings.lenientFormat(message, args));
    }

}
