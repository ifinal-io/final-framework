package org.ifinal.finalframework.service;

import java.io.Serializable;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.data.repository.Repository;
import org.springframework.lang.NonNull;

/**
 * 默认的{@link AbsService}实现，方便其子类通过 {@literal super}调用方法.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsServiceImpl<I extends Serializable, T extends IEntity<I>, R extends Repository<I, T>>
    implements AbsService<I, T, R> {

    private final R repository;

    protected AbsServiceImpl(final @NonNull R repository) {

        this.repository = repository;
    }

    @Override
    @NonNull
    public final R getRepository() {
        return repository;
    }

    /*=========================================== Overridable ===========================================*/

}

