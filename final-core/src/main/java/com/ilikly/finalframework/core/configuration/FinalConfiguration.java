package com.ilikly.finalframework.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-15 19:36:13
 * @since 1.0
 */
public class FinalConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(FinalConfiguration.class);
    private static final String PROPERTIES_PATH = "/final.properties";
    private static FinalConfiguration INSTANCE = new FinalConfiguration();

    private final Properties properties = new Properties();
    private boolean isConfiguration;

    private FinalConfiguration() {
        logger.info("==> 尝试加载配置文件：{}", PROPERTIES_PATH);
        ClassPathResource resource = new ClassPathResource(PROPERTIES_PATH);
        if (resource.exists()) {
            String filename = resource.getFilename();
            logger.trace("<== 找到配置文件：path={},name={}", resource.getPath(), filename);
            try {
                properties.load(resource.getInputStream());
                isConfiguration = true;
            } catch (IOException e) {
                logger.error("<== 加载配置文件异常：", e);
                isConfiguration = false;
            }
        } else {
            logger.info("<== 未找到配置文件：{}", PROPERTIES_PATH);
            isConfiguration = false;
        }
    }

    public static FinalConfiguration getInstance() {
        return INSTANCE;
    }

    public boolean isConfiguration() {
        return isConfiguration;
    }

    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
