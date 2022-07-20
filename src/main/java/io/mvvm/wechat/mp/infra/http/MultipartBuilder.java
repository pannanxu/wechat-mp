package io.mvvm.wechat.mp.infra.http;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @program: wechat-mp
 * @description: Http Form表单构建;
 * @author: Pan
 * @create: 2022-07-20 10:20
 **/
public class MultipartBuilder<T> {

    private final HttpEntityEnclosingRequest request;
    private final MultipartEntityBuilder     builder;
    private final T                          that;

    public MultipartBuilder(T that, HttpEntityEnclosingRequest request) {
        this.request = request;
        this.that = that;
        builder = MultipartEntityBuilder.create();
        builder.setCharset(StandardCharsets.UTF_8);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    }

    public MultipartBuilder<T> addFile(String name, String fileName, InputStream file) {
        builder.addBinaryBody(name, file, ContentType.MULTIPART_FORM_DATA, fileName);
        return this;
    }

    public MultipartBuilder<T> addText(String name, String text) {
        builder.addTextBody(name, text);
        return this;
    }

    public T build() {
        this.request.setEntity(builder.build());
        return this.that;
    }
}
