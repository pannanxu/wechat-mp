package io.mvvm.wechat.mp.manager.basic.apis;

import io.mvvm.wechat.mp.infra.GsonWrapper;
import io.mvvm.wechat.mp.manager.apis.BaseApi;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: wechat-mp
 * @description: 获取Access token
 * <p>
 * see: <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">官方文档</a>
 * @author: Pan
 * @create: 2022-07-14 20:26
 **/
@Slf4j
public class BasicSupportApi extends BaseApi {

    protected GsonWrapper requestAccessToken(String appId, String secret) {
        //        String response = "{\"access_token\":\"58_UdwoPX9nhWovrJdUfNCKsDNqQD-eJMCC8EB_qK_Lo222ZC0v3iA9dI8k_hCbGzZviKIQ5pTdMyspDutEzQE0chAogBQJmmM7pnbmG57rpkbcDmgnTz1a01ytzk3sRbVS0FGSbo9YuHIqsaUzNFGgAAAVNO\",\"expires_in\":7200}";
        //        return wrapper(response);
        return requestWrapper(() -> get("/cgi-bin/token")
                .setAppId(appId)
                .addParam("grant_type", "client_credential")
                .addParam("appid", appId)
                .addParam("secret", secret));
    }

}
