package io.mvvm.wechat.mp.infra.http;

import org.apache.http.client.methods.HttpGet;

import java.util.Map;

/**
 * @program: wechat-mp
 * @description: HTTP Get
 * @author: Pan
 * @create: 2022-07-14 19:02
 **/
public class Gets extends BaseHttp<Gets> {

    public static Gets request(String url) {
        return new Gets(url);
    }

    public static Gets request(String url, final Map<String, String> params) {
        return new Gets(url, params);
    }

    public Gets(String url) {
        super(new HttpGet(url));
    }

    public Gets(String url, final Map<String, String> params) {
        super(new HttpGet(url), params);
    }

    @Override
    protected Gets that() {
        return this;
    }
}
