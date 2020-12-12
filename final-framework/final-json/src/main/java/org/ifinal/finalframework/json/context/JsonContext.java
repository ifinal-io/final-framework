package org.ifinal.finalframework.json.context;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JsonContext {

    boolean isIgnore();

    void setIgnore(final boolean ignore);

    boolean isModifyEnum();

    void setModifyEnum(final boolean enable);

    boolean isModifyDate();

    void setModifyDate(final boolean enable);
}
