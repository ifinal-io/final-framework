/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.service;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.ParamsBuilder;
import org.ifinalframework.data.repository.Repository;
import org.ifinalframework.data.trigger.Trigger;
import org.ifinalframework.data.trigger.TriggerAction;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Setter;

/**
 * 默认的{@link AbsService}实现，方便其子类通过 {@literal super}调用方法.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsServiceImpl<I extends Serializable, T extends IEntity<I>>
        implements AbsService<I, T>, SmartInitializingSingleton, ApplicationContextAware {

    private Repository<I, T> repository;
    private final List<Trigger<T>> preInsertTriggers = new LinkedList<>();
    private final List<Trigger<T>> postInsertTriggers = new LinkedList<>();
    private final List<Trigger<T>> postSelectTriggers = new LinkedList<>();

    @Setter
    private ApplicationContext applicationContext;

    /**
     * Autodetect {@link Repository} when the callback of {@link SmartInitializingSingleton#afterSingletonsInstantiated()}.
     *
     * @see #autodetectRepository(Class, Class)
     * @see #afterSingletonsInstantiated()
     * @since 1.4.2
     */
    protected AbsServiceImpl() {

    }

    @Deprecated
    protected AbsServiceImpl(final Repository<I, T> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int insert(Map<String, Object> params) {
        List<T> entities = (List<T>) params.get(ParamsBuilder.LIST_PARAM_NAME);
        preInsertTriggers.forEach(it -> it.accept(TriggerAction.PRE_INSERT, entities));
        int rows = AbsService.super.insert(params);
        postInsertTriggers.forEach(it -> it.accept(TriggerAction.POST_INSERT, entities));
        return rows;
    }

    @Override
    public List<T> select(Map<String, Object> params) {
        List<T> entities = AbsService.super.select(params);

        postSelectTriggers.forEach(it -> it.accept(TriggerAction.POST_SELECT, entities));

        return entities;
    }

    @Override
    @NonNull
    public final Repository<I, T> getRepository() {
        return repository;
    }

    @Override
    public void afterSingletonsInstantiated() {
        autodetect();
    }

    private void autodetect() {
        ResolvableType repositoryResolvableType = ResolvableType.forInstance(this).as(Repository.class);
        Class<?> idClass = repositoryResolvableType.resolveGeneric(0);
        Class<?> entityClass = repositoryResolvableType.resolveGeneric(1);

        autodetectRepository(idClass, entityClass);
        autodetectTriggers(idClass, entityClass);
    }

    /**
     * @param id     id class
     * @param entity entity class
     * @since 1.4.2
     */
    @SuppressWarnings("unchecked")
    private void autodetectRepository(Class<?> id, Class<?> entity) {
        if (Objects.isNull(repository)) {
            ResolvableType repositoryType = ResolvableType.forClassWithGenerics(Repository.class, id, entity);
            this.repository = (Repository<I, T>) applicationContext.getBeanProvider(repositoryType).stream()
                    .filter(it -> !it.equals(this))
                    .filter(it -> !this.getClass().equals(AopUtils.getTargetClass(it)))
                    .findFirst().orElseThrow(() -> new BeanCreationException("not found repository for class " + entity));
        }
    }

    /**
     * @param id     id class
     * @param entity entity class
     * @since 1.4.2
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void autodetectTriggers(Class<?> id, Class<?> entity) {
        ResolvableType resolvableType = ResolvableType.forClassWithGenerics(Trigger.class, entity);
        applicationContext.getBeanProvider(resolvableType)
                .orderedStream()
                .map(it -> (Trigger<T>) it)
                .forEach(trigger -> {

                    Class<?> triggerClass = AopUtils.getTargetClass(trigger);

                    final String triggerName = triggerClass.getSimpleName();
                    if (triggerName.contains("PreInsert")) {
                        preInsertTriggers.add(trigger);
                    }

                    if (triggerName.contains("PostInsert")) {
                        postInsertTriggers.add(trigger);
                    }

                    if (triggerName.contains("PostSelect")) {
                        postSelectTriggers.add(trigger);
                    }

                });


    }


    /*=========================================== Overridable ===========================================*/

}

