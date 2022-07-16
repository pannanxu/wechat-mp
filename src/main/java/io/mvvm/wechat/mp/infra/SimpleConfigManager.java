package io.mvvm.wechat.mp.infra;

import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: wechat-mp
 * @description:
 * @author: Pan
 * @create: 2022-07-15 22:26
 **/
@Slf4j
public class SimpleConfigManager implements IConfigManager {

    private final Map<String, Optional<IConfig>> CONFIG_CONTAINER = new ConcurrentHashMap<>();

    public SimpleConfigManager() {
        initPropertiesConfig();
    }

    @Override
    public IConfig getConfig(String appId) {
        return CONFIG_CONTAINER.getOrDefault(appId, Optional.empty())
                .orElseThrow(() -> new RuntimeException("配置不存在"));
    }

    @Override
    public void setConfig(IConfig config) {
        log.info("save the config of '{}'", config.getAppId());
        CONFIG_CONTAINER.put(config.getAppId(), Optional.of(config));
    }

    private void initPropertiesConfig() {
        String defProperties = "wechat-mp.properties";
        log.info("load the config of '{}'.", defProperties);
        try {
            URL url = Resources.getResource(defProperties);
            Properties properties = new Properties();
            FileInputStream in = new FileInputStream(url.getPath());
            properties.load(in);
            String appId = properties.getProperty("wechat.appId");
            String secret = properties.getProperty("wechat.secret");
            setConfig(IConfig.of(appId, secret));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
