package org.ifinal.finalframework.annotation;

import org.ifinal.finalframework.annotation.data.Transient;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
public interface IRecord<ID extends Serializable, USER extends IUser<ID>> extends IEntity<ID> {

    /**
     * return the record creator
     *
     * @return the record creator
     */
    @Nullable
    USER getCreator();

    /**
     * set the record creator
     *
     * @param creator the record creator
     */
    void setCreator(@NonNull USER creator);

    /**
     * return the record last modifier.
     *
     * @return the record last modifier.
     */
    @Nullable
    USER getLastModifier();

    /**
     * set the record last modifier.
     *
     * @param modifier the record modifier.
     */
    void setLastModifier(@NonNull USER modifier);

}
