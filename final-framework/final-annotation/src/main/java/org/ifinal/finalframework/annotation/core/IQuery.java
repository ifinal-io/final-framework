package org.ifinal.finalframework.annotation.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.ifinal.finalframework.annotation.data.Transient;

/**
 * The marked {@code interface} of query to mark the target is a {@linkplain Object query}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY
)
@Transient
public interface IQuery {
}
