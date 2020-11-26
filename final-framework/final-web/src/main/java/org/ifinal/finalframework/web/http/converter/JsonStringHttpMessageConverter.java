package org.ifinal.finalframework.web.http.converter;

import org.ifinal.finalframework.json.Json;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;

/**
 * 包装 {@link StringHttpMessageConverter} 以解决使用 {@link org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice}
 * 方式处理{@link org.springframework.web.method.HandlerMethod} 返回类型与声明类型不一致时，导致抛出 {@link ClassCastException}。
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonStringHttpMessageConverter implements HttpMessageConverter<Object> {

    private final StringHttpMessageConverter converter;

    public JsonStringHttpMessageConverter(StringHttpMessageConverter converter) {
        this.converter = converter;
    }

    @Override
    public boolean canRead(@NonNull Class<?> clazz, MediaType mediaType) {
        return converter.canRead(clazz, mediaType);
    }

    @Override
    public boolean canWrite(@NonNull Class<?> clazz, MediaType mediaType) {
        return converter.canWrite(String.class, mediaType);
    }

    @NonNull
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return converter.getSupportedMediaTypes();
    }

    @NonNull
    @Override
    public Object read(@NonNull Class<?> clazz, @NonNull HttpInputMessage inputMessage) throws IOException {
        return converter.read(String.class, inputMessage);
    }

    @Override
    public void write(@NonNull Object o, MediaType contentType, @NonNull HttpOutputMessage outputMessage) throws IOException {
        converter.write(Json.toJson(o), contentType, outputMessage);
    }
}
