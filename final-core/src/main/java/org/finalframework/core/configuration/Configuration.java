package org.finalframework.core.configuration;

import org.springframework.core.io.ClassPathResource;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-15 19:36:13
 * @since 1.0
 */
public class Configuration {
    private static final String PROPERTIES_PATH = "final.properties";
    private static Configuration INSTANCE = new Configuration();

    private final Properties properties = new Properties();
    private boolean isConfiguration;

    private Configuration() {
        ClassPathResource resource = new ClassPathResource("/" + PROPERTIES_PATH);
        if (resource.exists()) {
            try {
                properties.load(getClass().getResourceAsStream(PROPERTIES_PATH));
                isConfiguration = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            isConfiguration = false;
        }
    }

    public static Configuration getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Configuration configuration = Configuration.getInstance();
        System.out.println();
    }

    public boolean isConfiguration() {
        return isConfiguration;
    }

    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Java 编译时加载配置文件
     *
     * @param processingEnv 编译环境
     */
    public void load(ProcessingEnvironment processingEnv) {
        try {
            FileObject resource = processingEnv.getFiler().getResource(StandardLocation.CLASS_PATH, "", PROPERTIES_PATH);
            properties.load(resource.openInputStream());
        } catch (Exception e) {
            // ignore
        }
    }


}
