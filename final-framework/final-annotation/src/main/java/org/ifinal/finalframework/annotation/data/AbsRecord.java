package org.ifinal.finalframework.annotation.data;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ifinal.finalframework.annotation.core.IRecord;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AbsRecord extends AbsEntity implements IRecord<Long, AbsUser> {

    @Creator
    @Reference(properties = {"id", "name"})
    private AbsUser creator;
    @LastModifier
    @Reference(properties = {"id", "name"})
    private AbsUser lastModifier;

}

