package org.finalframework.annotation.data;


import org.finalframework.annotation.IRecord;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:23:19
 * @since 1.0
 */
@Transient
public class AbsRecord extends AbsEntity implements IRecord<Long, AbsUser> {

    @Creator
    @Reference(properties = {"id", "name"})
    private AbsUser creator;
    @LastModifier
    @Reference(properties = {"id", "name"})
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

