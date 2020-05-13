package org.finalframework.document.api.service.converter;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.finalframework.core.converter.Converter;
import org.finalframework.document.api.entity.ApiDocuments;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sli
 * @version 1.0
 * @date 2020-05-13 21:53:46
 * @since 1.0
 */
public class RestController2ApiDocumentsConverter implements Converter<Class<?>, ApiDocuments> {


    private final HandlerMethod2ApiDocumentConverter converter = new HandlerMethod2ApiDocumentConverter();


    @Override
    public ApiDocuments convert(@NonNull Class<?> source) {
        ApiDocuments documents = new ApiDocuments();

        documents.setTarget(source);

        Optional.ofNullable(source.getAnnotation(RequestMapping.class))
            .ifPresent(requestMapping -> documents.setPath(requestMapping.value()));

        documents.setApiDocuments(
            Arrays.stream(source.getMethods())
                .filter(method -> AnnotatedElementUtils.isAnnotated(method, RequestMapping.class))
                .map(method -> converter.convert(method, documents))
                .collect(Collectors.toList())
        );

        return documents;
    }
}
