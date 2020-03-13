package org.finalframework.data.entity;


import org.finalframework.data.annotation.ReferenceColumn;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:23:19
 * @since 1.0
 */
public class AbsRecord extends AbsEntity implements IRecord<Long, AbsUser> {
    @ReferenceColumn(properties = {"id", "name"})
    private AbsUser creator;
    @ReferenceColumn(properties = {"id", "name"})
    private AbsUser lastModifier;

    @Override
    public AbsUser getCreator() {
        return creator;
    }

    @Override
    public void setCreator(AbsUser creator) {
        this.creator = creator;
    }

    @Override
    public AbsUser getLastModifier() {
        return lastModifier;
    }

    @Override
    public void setLastModifier(AbsUser lastModifier) {
        this.lastModifier = lastModifier;
    }
}

