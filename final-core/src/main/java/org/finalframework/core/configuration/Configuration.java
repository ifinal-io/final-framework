package org.finalframework.core.configuration;

import org.springframework.core.io.ClassPathResource;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.util.Properties;
import java.util.function.BiConsumer;

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
                properties.load(resource.getInputStream());
                isConfiguration = true;
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
        Properties properties = configuration.properties;
        properties.forEach(new BiConsumer<Object, Object>() {
            @Override
            public void accept(Object key, Object value) {
                System.out.println(String.format("key=%s,value=%s", key, value));
            }
        });

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
            FileObject resource = processingEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT, "", PROPERTIES_PATH);
            properties.load(resource.openInputStream());
            System.out.println("======" + resource.toUri().toString());
            for (Object key : properties.keySet()) {
                System.out.println(String.format("%s=%s", key, getString(key.toString(), null)));
            }
        } catch (Exception e) {
            // ignore
            e.printStackTrace();
        }
    }


}
