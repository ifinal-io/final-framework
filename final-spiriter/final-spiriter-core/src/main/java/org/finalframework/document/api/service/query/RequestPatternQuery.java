

package org.finalframework.document.api.service.query;


import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 09:25:26
 * @since 1.0
 */
@Data
public class RequestPatternQuery {
    @Nullable
    private RequestMethod method;
    @Nullable
    private String pattern;
}

