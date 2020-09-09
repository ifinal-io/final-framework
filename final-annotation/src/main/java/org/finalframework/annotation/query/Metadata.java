

package org.finalframework.annotation.query;


import lombok.Builder;
import lombok.Data;
import org.finalframework.annotation.data.TypeHandler;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 17:47:55
 * @see CriterionHandler
 * @see Criterion
 * @since 1.0
 */
@Data
@Builder
public class Metadata implements Serializable {
    @NonNull
    private AndOr andOr;
    @NonNull
    private String query;
    @NonNull
    private String column;
    @NonNull
    private String value;
    @NonNull
    private String path;
    private Class<?> javaType;
    @Nullable
    private Class<? extends TypeHandler> typeHandler;
    private Map<String, String> attributes;
}

