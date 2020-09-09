

package org.finalframework.document.api.entity;

import java.util.List;

import lombok.Data;

/**
 * @author sli
 * @version 1.0
 * @date 2020-05-13 21:52:16
 * @since 1.0
 */
@Data
public class ApiDocuments {

    private Class<?> target;
    private String group;
    private String[] path;
    private List<ApiDocument> apiDocuments;
}
