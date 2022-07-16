package io.mvvm.wechat.mp.manager.basic.apis;

import io.mvvm.wechat.mp.infra.GsonWrapper;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-16 21:24
 **/
public class MockBasicSupportApi extends BasicSupportApi {
    @Override
    protected GsonWrapper requestAccessToken(String appId, String secret) {
        String response = "{\"access_token\":\"58_UdwoPX9nhWovrJdUfNCKsDNqQD-eJMCC8EB_qK_Lo222ZC0v3iA9dI8k_hCbGzZviKIQ5pTdMyspDutEzQE0chAogBQJmmM7pnbmG57rpkbcDmgnTz1a01ytzk3sRbVS0FGSbo9YuHIqsaUzNFGgAAAVNO\",\"expires_in\":7200}";
        return wrapper(response);
    }
}
