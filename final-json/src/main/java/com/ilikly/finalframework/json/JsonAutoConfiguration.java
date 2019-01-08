package com.ilikly.finalframework.json;

import com.ilikly.finalframework.coding.plugins.spring.annotation.AutoConfiguration;
import com.ilikly.finalframework.json.fastjson.FastJsonService;
import com.ilikly.finalframework.json.gson.GsonJsonService;
import com.ilikly.finalframework.json.jackson.JacksonJsonService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:16:57
 * @since 1.0
 */
@AutoConfiguration
public class JsonAutoConfiguration {

    @Bean("jsonService")
    @ConditionalOnMissingBean(JsonService.class)
    public JsonService jacksonJsonService() {
        return new JacksonJsonService();
    }

    @Bean("jsonService")
    @ConditionalOnMissingBean(JsonService.class)
    public JsonService gsonJsonService() {
        return new GsonJsonService();
    }

    @Bean("jsonService")
    @ConditionalOnMissingBean(JsonService.class)
    public JsonService fastJsonService() {
        return new FastJsonService();
    }
}
