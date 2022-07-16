WeChat MP for Java
=====================

# Using

```java
String appId = "you wechat mp appId";
IWechatMpApi api = new SimpleWechatMpApi();
IAccessTokenManager accessTokenManager = api.getAccessTokenManager();
String accessToken = accessTokenManager.getAccessToken(appId);
```