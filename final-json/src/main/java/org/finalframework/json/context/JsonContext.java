package org.finalframework.json.context;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-20 22:38:44
 * @since 1.0
 */
public interface JsonContext {

    boolean isIgnore();

    void setIgnore(boolean ignore);

    boolean isModifyEnum();

    void setModifyEnum(boolean enable);

    boolean isModifyDate();

    void setModifyDate(boolean enable);
}
