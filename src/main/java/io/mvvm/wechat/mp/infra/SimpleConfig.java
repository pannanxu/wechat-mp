package io.mvvm.wechat.mp.infra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-14 20:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleConfig implements IConfig {

    private String appId;

    private String secret;

}
