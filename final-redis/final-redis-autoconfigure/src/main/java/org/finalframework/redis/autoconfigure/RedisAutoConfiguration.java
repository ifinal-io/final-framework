/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.redis.autoconfigure;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.finalframework.json.jackson.FinalJacksonModule;
import org.finalframework.redis.RedisRegistry;
import org.finalframework.redis.serializer.Object2JsonRedisSerializer;
import org.finalframework.redis.serializer.Object2StringRedisSerializer;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 14:10:58
 * @since 1.0
 */
@Configuration
@SpringConfiguration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration implements ApplicationContextAware {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RedisProperties redisProperties;
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        redisTemplate.setKeySerializer(applicationContext.getBean(redisProperties.getKeySerializer()));
        redisTemplate.setValueSerializer(applicationContext.getBean(redisProperties.getValueSerializer()));
        redisTemplate.setHashKeySerializer(applicationContext.getBean(redisProperties.getHashKeySerializer()));
        redisTemplate.setHashValueSerializer(applicationContext.getBean(redisProperties.getHashValueSerializer()));
        RedisRegistry.getInstance().setRedisTemplate(redisTemplate);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new FinalJacksonModule(objectMapper));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//        objectMapper.registerModule(new SimpleModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}
