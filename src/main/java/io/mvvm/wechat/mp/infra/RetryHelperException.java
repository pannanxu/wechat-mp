package io.mvvm.wechat.mp.infra;

/**
 * @program: wechat-mp
 * @description: 重试请求异常, 通常收到此类型的异常表示接口多次重试失败
 * @author: Pan
 * @create: 2022-07-16 16:32
 **/
public class RetryHelperException extends RuntimeException {
    public RetryHelperException() {
    }

    public RetryHelperException(String message) {
        super(message);
    }

    public RetryHelperException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryHelperException(Throwable cause) {
        super(cause);
    }

    public RetryHelperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
