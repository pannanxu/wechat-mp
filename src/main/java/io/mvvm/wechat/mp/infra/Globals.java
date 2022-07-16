package io.mvvm.wechat.mp.infra;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public final class Globals {
    /**
     * 需要刷新AccessToken的错误码
     */
    public static final List<String> REFRESH_ACCESS_TOKEN_CODES = Lists.newArrayList("41001", "42001");
    /**
     * 重试请求的错误码
     */
    public static final List<String> RETRY_CODES = Lists.newArrayList("40014", "42001", "42002", "42003", "45011");
    /**
     * 抛出异常的错误码
     */
    public static final List<String> THROW_CODES = Lists.newArrayList("40001", "40125", "40164", "40002");

    /**
     * 全局返回码说明
     * <p>
     * see: <a href="https://developers.weixin.qq.com/doc/offiaccount/Getting_Started/Global_Return_Code.html">全局返回码说明</a>
     */
    @AllArgsConstructor
    @Getter
    public static enum ReturnCode {
        other("-n", "message"),
        sys_err("-1", "系统繁忙，请稍后重试"),
        success("0", "请求成功"),
        get_access_token_invalid_secret ("40001", "获取 access_token 时 AppSecret 错误"),
        not_in_whitelist("40164", "客户端IP不在白名单"),
        invalid_secret("40125", "错误的Secret"),
        ;

        private final String code;
        private final String desc;

        public static ReturnCode getInstance(String code) {
            return Arrays.stream(ReturnCode.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(other);
        }
    }
}
