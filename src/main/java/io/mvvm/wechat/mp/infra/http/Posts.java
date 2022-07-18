package io.mvvm.wechat.mp.infra.http;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import java.util.Map;

/**
 * @program: wechat-mp
 * @description: HTTP Post
 * @author: Pan
 * @create: 2022-07-14 19:19
 **/
public class Posts extends BaseHttp<Posts> {

    public static Posts request(String url) {
        return new Posts(url);
    }

    public static Posts request(String url, byte[] body) {
        HttpPost post = new HttpPost(url);
        post.setEntity(new ByteArrayEntity(body));
        return new Posts(post);
    }

    public static Posts request(String url, final Map<String, String> params) {
        return new Posts(url, params);
    }

    public Posts(String url) {
        super(new HttpPost(url));
    }

    public Posts(HttpPost post) {
        super(post);
    }

    public Posts(String url, Map<String, String> params) {
        super(new HttpPost(url), params);
    }

    @Override
    protected Posts that() {
        return this;
    }

}
