package org.ifinal.finalframework.json.context;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleJsonContext implements JsonContext {

    private boolean ignore;

    private boolean modifyEnum;

    private boolean modifyDate;

    public SimpleJsonContext() {
        this(false, true, true);
    }

    public SimpleJsonContext(final boolean ignore, final boolean modifyEnum, final boolean modifyDate) {

        this.ignore = ignore;
        this.modifyEnum = modifyEnum;
        this.modifyDate = modifyDate;
    }

    @Override
    public boolean isIgnore() {
        return ignore;
    }

    @Override
    public void setIgnore(final boolean ignore) {

        this.ignore = ignore;
    }

    @Override
    public boolean isModifyEnum() {
        return modifyEnum;
    }

    @Override
    public void setModifyEnum(final boolean modifyEnum) {

        this.modifyEnum = modifyEnum;
    }

    @Override
    public boolean isModifyDate() {
        return modifyDate;
    }

    @Override
    public void setModifyDate(final boolean modifyDate) {

        this.modifyDate = modifyDate;
    }

}
