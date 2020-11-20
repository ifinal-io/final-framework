package org.finalframework.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The marked {@code interface} of query to mark the target is a {@linkplain Object query}.
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-12 12:41:09
 * @since 1.0
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY
)
public interface IQuery {
}
