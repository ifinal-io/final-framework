

package org.finalframework.spring.web.http.converter;

import org.finalframework.json.Json;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.io.IOException;
import java.util.List;

/**
 * 包装 {@link StringHttpMessageConverter} 以解决使用 {@link org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice}
 * 方式处理{@link org.springframework.web.method.HandlerMethod} 返回类型与声明类型不一致时，导致抛出 {@link ClassCastException}。
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-24 16:07:42
 * @since 1.0
 */
public class JsonStringHttpMessageConverter implements HttpMessageConverter<Object> {

    private final StringHttpMessageConverter converter;

    public JsonStringHttpMessageConverter(StringHttpMessageConverter converter) {
        this.converter = converter;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return converter.canRead(clazz, mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return converter.canWrite(String.class, mediaType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return converter.getSupportedMediaTypes();
    }

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return converter.read(String.class, inputMessage);
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        converter.write(Json.toJson(o), contentType, outputMessage);
    }
}
