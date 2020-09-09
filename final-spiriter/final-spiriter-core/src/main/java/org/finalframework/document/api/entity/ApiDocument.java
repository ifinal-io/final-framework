

package org.finalframework.document.api.entity;

import java.lang.reflect.Method;
import java.util.List;

import lombok.Data;

/**
 * @author sli
 * @version 1.0
 * @date 2020-05-13 21:44:34
 * @since 1.0
 */
@Data
public class ApiDocument {

    private Class<?> target;
    private Method method;
    private String[] path;
    private List<String> urls;

}
