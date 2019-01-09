package com.ilikly.finalframework.json.autoconfigure;

import com.ilikly.finalframework.json.JsonService;
import com.ilikly.finalframework.json.jackson.JacksonJsonService;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 22:02:17
 * @since 1.0
 */
@ConfigurationProperties(JsonProperties.JSON_PROPERTIES)
public class JsonProperties {
    public static final String JSON_PROPERTIES = "final.json";

    private Class<? extends JsonService> jsonService = JacksonJsonService.class;

    public Class<? extends JsonService> getJsonService() {
        return jsonService;
    }

    public void setJsonService(Class<? extends JsonService> jsonService) {
        this.jsonService = jsonService;
    }
}
