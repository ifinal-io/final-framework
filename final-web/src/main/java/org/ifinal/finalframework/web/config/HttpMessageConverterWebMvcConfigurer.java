package org.ifinal.finalframework.web.config;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.web.http.converter.JsonStringHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * HttpMessageConverterWebMvcConfigurer.
 *
 * @author likly
 * @version 1.0.0
 * @see WebMvcConfigurer#configureMessageConverters(List)
 * @see WebMvcConfigurer#extendMessageConverters(List)
 * @since 1.0.0
 */
@Slf4j
@Component
public class HttpMessageConverterWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * Replace the {@link StringHttpMessageConverter} to {@link JsonStringHttpMessageConverter}.
     *
     * @param converters the build-in {@link HttpMessageConverter}s.
     */
    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {

        for (int i = 0; i < converters.size(); i++) {
            HttpMessageConverter<?> converter = converters.get(i);
            if (converter instanceof StringHttpMessageConverter) {
                // Use JsonStringHttpMessageConverter replace StringHttpMessageConverter.
                converter = new JsonStringHttpMessageConverter((StringHttpMessageConverter) converter);
                converters.set(i, converter);
            }
        }

    }

}
