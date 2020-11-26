package org.ifinal.finalframework.json.context;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JsonContext {

    boolean isIgnore();

    void setIgnore(boolean ignore);

    boolean isModifyEnum();

    void setModifyEnum(boolean enable);

    boolean isModifyDate();

    void setModifyDate(boolean enable);
}
