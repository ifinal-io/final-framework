package org.finalframework.retrofit.autoconfigure;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 17:10:58
 * @since 1.0
 */
@Data
@ConfigurationProperties(RetrofitProperties.RETROFIT_PREFIX)
public class RetrofitProperties implements Serializable {
    public static final String RETROFIT_PREFIX = "final.retrofit";
    private String baseUrl;
    private OkHttpProperties okHttp;

    /**
     * @author likly
     * @version 1.0
     * @date 2020-04-25 18:55:37
     * @since 1.0
     */
    @Setter
    @Getter
    public static class OkHttpProperties implements Serializable {
        private Long connectTimeout = 5000L;
        private Long readTimeout = 2000L;
        private Long writeTimeout = 2000L;


    }
}

