package org.ifinal.finalframework.data.service;

import org.ifinal.finalframework.annotation.IEntity;
import org.ifinal.finalframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.io.Serializable;


/**
 * 默认的{@link AbsService}实现，方便其子类通过 {@literal super}调用方法
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsServiceImpl<ID extends Serializable, T extends IEntity<ID>, R extends Repository<ID, T>> implements AbsService<ID, T, R> {

    private final R repository;

    public AbsServiceImpl(@NonNull R repository) {
        this.repository = repository;
    }

    @Override
    @NonNull
    public final R getRepository() {
        return repository;
    }

    /*=========================================== Overridable ===========================================*/


}

