package org.finalframework.document.api.service.converter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.finalframework.core.converter.Converter2;
import org.finalframework.document.api.entity.ApiDocument;
import org.finalframework.document.api.entity.ApiDocuments;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sli
 * @version 1.0
 * @date 2020-05-13 22:05:08
 * @since 1.0
 */
public class HandlerMethod2ApiDocumentConverter implements Converter2<Method, ApiDocuments, ApiDocument> {

    @Override
    public ApiDocument convert(Method method, ApiDocuments apiDocuments) {
        final ApiDocument document = new ApiDocument();

        document.setTarget(apiDocuments.getTarget());
        document.setMethod(method);

        Optional.ofNullable(AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class))
            .ifPresent(requestMapping -> {
                document.setPath(requestMapping.path());
                document.setUrls(formatUrls(apiDocuments.getPath(), requestMapping.path()));


            });

        return document;
    }

    private List<String> formatUrls(String[] prefixPaths, String[] paths) {
        List<String> urls = new ArrayList<>();

        if (prefixPaths.length == 0 && paths.length == 0) {
            urls.add("/");
        } else if (prefixPaths.length == 0 && paths.length > 0) {
            for (String path : paths) {
                urls.add(path);
            }
        } else if (prefixPaths.length > 0 && paths.length == 0) {
            for (String path : prefixPaths) {
                urls.add(path);
            }
        } else {
            for (String prefixPath : prefixPaths) {
                for (String path : paths) {
                    urls.add(prefixPath + path);
                }
            }
        }

        return urls;
    }

}
