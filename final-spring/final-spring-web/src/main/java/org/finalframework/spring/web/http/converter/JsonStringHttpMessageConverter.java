package org.finalframework.spring.web.http.converter;

import org.finalframework.json.Json;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-24 16:07:42
 * @since 1.0
 */
public class JsonStringHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    /**
     * The default charset used by the converter.
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.ISO_8859_1;


    @Nullable
    private volatile List<Charset> availableCharsets;

    private boolean writeAcceptCharset = true;

    public JsonStringHttpMessageConverter() {
        this(DEFAULT_CHARSET);
    }

    /**
     * A constructor accepting a default charset to use if the requested content
     * type does not specify one.
     */
    public JsonStringHttpMessageConverter(Charset defaultCharset) {
        super(defaultCharset, MediaType.TEXT_PLAIN, MediaType.ALL);
    }


    /**
     * Indicates whether the {@code Accept-Charset} should be written to any outgoing request.
     * <p>Default is {@code true}.
     */
    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        this.writeAcceptCharset = writeAcceptCharset;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        String json = StreamUtils.copyToString(inputMessage.getBody(), charset);
        return Json.toObject(json, clazz);
    }

    @Override
    protected Long getContentLength(Object object, @Nullable MediaType contentType) {
        Charset charset = getContentTypeCharset(contentType);
        return (long) Json.toJson(object).getBytes(charset).length;
    }

    @Override
    protected void writeInternal(Object str, HttpOutputMessage outputMessage) throws IOException {
        if (this.writeAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
        }
        Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
        StreamUtils.copy(Json.toJson(str), charset, outputMessage.getBody());
    }


    /**
     * Return the list of supported {@link Charset Charsets}.
     * <p>By default, returns {@link Charset#availableCharsets()}.
     * Can be overridden in subclasses.
     *
     * @return the list of accepted charsets
     */
    protected List<Charset> getAcceptedCharsets() {
        List<Charset> charsets = this.availableCharsets;
        if (charsets == null) {
            charsets = new ArrayList<>(Charset.availableCharsets().values());
            this.availableCharsets = charsets;
        }
        return charsets;
    }

    private Charset getContentTypeCharset(@Nullable MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        } else {
            Charset charset = getDefaultCharset();
            Assert.state(charset != null, "No default charset");
            return charset;
        }
    }
}
