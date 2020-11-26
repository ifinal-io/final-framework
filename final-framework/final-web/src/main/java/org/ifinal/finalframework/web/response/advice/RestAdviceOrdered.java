package org.ifinal.finalframework.web.response.advice;

import org.springframework.core.Ordered;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface RestAdviceOrdered {
    /**
     * @see Integer#MIN_VALUE
     */
    int HIGHEST_PRECEDENCE = Ordered.HIGHEST_PRECEDENCE;
    int DEFAULT_PRECEDENCE = 0;
    int JSON_VIEW_PRECEDENCE = 800;
    int RESULT_PRECEDENCE = 1000;
    /**
     * @see Integer#MAX_VALUE
     */
    int LOWEST_PRECEDENCE = Ordered.LOWEST_PRECEDENCE;

}
