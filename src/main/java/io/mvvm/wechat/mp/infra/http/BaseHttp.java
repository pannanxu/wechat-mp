package io.mvvm.wechat.mp.infra.http;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-14 19:09
 **/
@Slf4j
public abstract class BaseHttp<T> {

    protected     HttpRequestBase                  http;
    private final Map<String, String>              params;
    private       BasicHttpClientConnectionManager connectionManager;

    public BaseHttp(HttpRequestBase http) {
        this.http = http;
        this.params = new ConcurrentHashMap<>();
    }

    public BaseHttp(HttpRequestBase http, Map<String, String> params) {
        this.params = params;
        this.http = http;
    }

    protected abstract T that();

    public T addParam(String name, String value) {
        this.params.put(name, value);
        return this.that();
    }

    public T addParams(final Map<String, String> params) {
        this.params.putAll(params);
        return this.that();
    }

    public T addHeader(final String name, final String value) {
        this.http.addHeader(name, value);
        return this.that();
    }

    public T ssl() {
        connectionManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build(), null, null, null);
        return this.that();
    }

    public T ssl(final String pwd, final InputStream in) {
        try {
            connectionManager = buildConnManager(pwd, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.that();
    }

    public T proxy() {
        return this.that();
    }

    public String getString() {
        resetURI();
        log.info("The api of this request is '{}'.", http.getURI().toString());
        try (CloseableHttpClient client = HttpClientBuilder.create().setConnectionManager(connectionManager).build()) {
            CloseableHttpResponse response = client.execute(this.http);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            log.info("The result of the request is '{}'.", result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream getContent() {
        resetURI();
        log.info("The api of this request is '{}'", http.getURI().toString());
        try (CloseableHttpClient client = HttpClientBuilder.create().setConnectionManager(connectionManager).build()) {
            CloseableHttpResponse response = client.execute(this.http);
            HttpEntity entity = response.getEntity();
            return entity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void resetURI() {
        if (this.params.isEmpty()) {
            return;
        }

        StringBuilder param = new StringBuilder();
        this.params.forEach((name, value) -> param.append(name).append("=").append(value).append("&"));
        param.delete(param.length() - 1, param.length());

        URI uri = this.http.getURI();
        String query = uri.getQuery();

        String url = uri + // ln
                (Strings.isNullOrEmpty(query) ? "?" // ln
                        : (uri.toString().lastIndexOf("&") == -1 ? "&" : ""));

        try {
            this.http.setURI(new URI(url + param));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private BasicHttpClientConnectionManager buildConnManager(String pwd, InputStream certStream) throws Exception {
        char[] password = pwd.toCharArray();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, password);
        // 实例化密钥库 & 初始化密钥工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);
        // 创建 SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null, new DefaultHostnameVerifier());
        return new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslConnectionSocketFactory).build(), null, null, null);
    }

}
