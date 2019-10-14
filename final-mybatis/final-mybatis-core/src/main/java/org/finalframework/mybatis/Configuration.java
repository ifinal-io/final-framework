package org.finalframework.mybatis;

import lombok.Data;
import org.finalframework.mybatis.handler.JsonObjectTypeHandler;

import java.util.Properties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 16:09:06
 * @since 1.0
 */
@Data
public class Configuration {
    private static final String FINAL_MYBATIS_TYPE_HANDLER_JSON_OBJECT_TYPE_HANDLER = "final.mybatis.typeHandler.jsonObjectTypeHandler";

    private static final Configuration instance = new Configuration();

    private String jsonObjectTypeHandler;

    public Configuration() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream("/final.properties"));
            this.jsonObjectTypeHandler = properties.getProperty(FINAL_MYBATIS_TYPE_HANDLER_JSON_OBJECT_TYPE_HANDLER, JsonObjectTypeHandler.class.getCanonicalName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Configuration getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        Configuration instance = Configuration.getInstance();
        System.out.println();
    }
}
