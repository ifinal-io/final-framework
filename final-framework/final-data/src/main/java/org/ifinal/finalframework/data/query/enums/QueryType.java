package org.ifinal.finalframework.data.query.enums;

import org.ifinal.finalframework.annotation.data.Transient;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
public enum QueryType {
    SELECT("select"),
    SELECT_ONE("select"),
    SELECT_COUNT("select");
    private final String tag;

    QueryType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
