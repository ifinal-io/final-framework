package org.ifinal.finalframework.annotation.core;

import java.io.Serializable;
import org.ifinal.finalframework.annotation.data.Transient;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
public interface IRecord<I extends Serializable, U extends IUser<I>> extends IEntity<I> {

    /**
     * return the record creator
     *
     * @return the record creator
     */
    @Nullable
    U getCreator();

    /**
     * set the record creator
     *
     * @param creator the record creator
     */
    void setCreator(@NonNull U creator);

    /**
     * return the record last modifier.
     *
     * @return the record last modifier.
     */
    @Nullable
    U getLastModifier();

    /**
     * set the record last modifier.
     *
     * @param modifier the record modifier.
     */
    void setLastModifier(@NonNull U modifier);

}
