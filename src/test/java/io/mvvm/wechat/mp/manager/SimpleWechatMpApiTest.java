package io.mvvm.wechat.mp.manager;

import io.mvvm.wechat.mp.manager.basic.IAccessTokenManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleWechatMpApiTest {

    public static final String appId = "wx70c1c4112b289e9e";

    private static final IWechatMpApi api = new SimpleWechatMpApi();

    @BeforeAll
    public static void setUp() {
    }

    @Test
    public void getAccessToken() {
        IAccessTokenManager accessTokenManager = api.getAccessTokenManager();
        String accessToken = accessTokenManager.getAccessToken(appId);
        System.out.println(accessToken);
        assertNotNull(accessToken);
    }

    @Test
    public void getDomainIp() {
        IApiDomainManager apiDomainManager = api.getApiDomainManager();
        List<String> apiDomainIp = apiDomainManager.getApiDomainIp(appId);
        System.out.println(apiDomainIp);
    }

    @Test
    public void createTag() {
        IUserManager userManager = api.getUserManager();
        String tagId = userManager.createTag(appId, "hello");
        System.out.println(tagId);
        assertNotNull(tagId);
    }

}