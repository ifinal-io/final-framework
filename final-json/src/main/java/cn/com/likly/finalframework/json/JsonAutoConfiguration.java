package cn.com.likly.finalframework.json;

import cn.com.likly.finalframework.coding.plugins.spring.annotation.AutoConfiguration;
import cn.com.likly.finalframework.json.fastjson.FastJsonService;
import cn.com.likly.finalframework.json.gson.GsonJsonService;
import cn.com.likly.finalframework.json.jackson.JacksonJsonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
    @ConditionalOnBean(ObjectMapper.class)
    @ConditionalOnMissingBean(JsonService.class)
    public JacksonJsonService jacksonJsonService() {
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
