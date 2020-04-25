package org.finalframework.retrofit.autoconfigure;


import org.finalframework.core.Assert;
import org.finalframework.retrofit.RetrofitFactoryBean;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import retrofit2.Converter;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 17:13:57
 * @since 1.0
 */
@Configuration
@SpringConfiguration
@ConditionalOnMissingBean(Retrofit.class)
@ConditionalOnClass(Retrofit.class)
@EnableConfigurationProperties(RetrofitProperties.class)
public class RetrofitAutoConfiguration implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(RetrofitAutoConfiguration.class);
    private final RetrofitProperties properties;
    private final List<Converter.Factory> converterFactories;

    public RetrofitAutoConfiguration(RetrofitProperties properties, ObjectProvider<List<Converter.Factory>> converterFactoriesProvider) {
        this.properties = properties;
        this.converterFactories = converterFactoriesProvider.getIfAvailable();
    }

    @Bean
    public Retrofit retrofit(RetrofitProperties properties) {
        final Retrofit.Builder builder = new Retrofit.Builder();

        if (StringUtils.hasText(properties.getBaseUrl())) {
            builder.baseUrl(properties.getBaseUrl());
        }

        if (Assert.nonEmpty(converterFactories)) {
            converterFactories.forEach(builder::addConverterFactory);
        }
//        final RetrofitProperties.OkHttpProperties okHttpProperties = properties.getOkHttp();
//        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
//                .connectTimeout(okHttpProperties.getConnectTimeout(), TimeUnit.MILLISECONDS)
//                .readTimeout(okHttpProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
//                .writeTimeout(okHttpProperties.getWriteTimeout(), TimeUnit.MILLISECONDS);
//
//        builder.client(clientBuilder.build());

        return builder.build();
    }


    @Configuration
    @Import({RetrofitAutoConfiguredScannerRegistrar.class})
    @ConditionalOnMissingBean(RetrofitFactoryBean.class)
    public static class MapperScannerRegistrarNotFoundConfiguration {

        @PostConstruct
        public void afterPropertiesSet() {
            logger.debug("No {} found.", RetrofitFactoryBean.class.getName());
        }
    }
}

