package org.finalframework.data.service;

import org.finalframework.annotation.IEntity;
import org.finalframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.io.Serializable;


/**
 * 默认的{@link AbsService}实现，方便其子类通过 {@literal super}调用方法
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-18 16:16:49
 * @since 1.0
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

