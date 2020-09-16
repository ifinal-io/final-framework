package org.finalframework.json.context;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-20 22:53:06
 * @since 1.0
 */
public class SimpleJsonContext implements JsonContext {

    private boolean ignore;
    private boolean modifyEnum;
    private boolean modifyDate;

    public SimpleJsonContext() {
        this(false, true, true);
    }

    public SimpleJsonContext(boolean ignore, boolean modifyEnum, boolean modifyDate) {
        this.ignore = ignore;
        this.modifyEnum = modifyEnum;
        this.modifyDate = modifyDate;
    }

    @Override
    public boolean isIgnore() {
        return ignore;
    }

    @Override
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    @Override
    public boolean isModifyEnum() {
        return modifyEnum;
    }

    @Override
    public void setModifyEnum(boolean modifyEnum) {
        this.modifyEnum = modifyEnum;
    }

    @Override
    public boolean isModifyDate() {
        return modifyDate;
    }

    @Override
    public void setModifyDate(boolean modifyDate) {
        this.modifyDate = modifyDate;
    }
}
