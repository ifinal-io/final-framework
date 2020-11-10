package org.finalframework.annotation.query;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 09:25:40
 * @since 1.0
 */
public interface CriterionHandler {
    String handle(Criterion criterion, Metadata metadata);
}
