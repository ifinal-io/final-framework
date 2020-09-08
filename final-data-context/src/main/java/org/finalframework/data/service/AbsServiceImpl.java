/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.data.service;

import org.finalframework.data.annotation.IEntity;
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
