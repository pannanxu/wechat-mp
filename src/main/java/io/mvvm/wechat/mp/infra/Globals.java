package io.mvvm.wechat.mp.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

public final class Globals {


    /**
     * 全局返回码说明
     * <p>
     * see: <a href="https://developers.weixin.qq.com/doc/offiaccount/Getting_Started/Global_Return_Code.html">全局返回码说明</a>
     */
    @AllArgsConstructor
    @Getter
    public static enum ReturnCode {
        sys_err("-1", "系统繁忙，请稍后重试"),
        success("0", "请求成功"),
        not_in_whitelist("40164", "客户端IP不在白名单"),
        ;

        private final String code;
        private final String desc;

        public static ReturnCode getInstance(String code) {
            return Arrays.stream(ReturnCode.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(sys_err);
        }
    }
}
