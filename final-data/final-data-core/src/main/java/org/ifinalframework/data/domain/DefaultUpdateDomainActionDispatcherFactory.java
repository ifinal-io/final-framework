/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.domain;


import org.springframework.aop.support.AopUtils;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.data.domain.action.UpdateAction;
import org.ifinalframework.data.domain.function.DefaultUpdateFunction;
import org.ifinalframework.data.domain.function.DefaultUpdatePropertyFunction;
import org.ifinalframework.data.repository.Repository;
import org.ifinalframework.data.spi.AfterConsumer;
import org.ifinalframework.data.spi.BiAfterReturningConsumer;
import org.ifinalframework.data.spi.BiAfterThrowingConsumer;
import org.ifinalframework.data.spi.BiConsumer;
import org.ifinalframework.data.spi.BiValidator;
import org.ifinalframework.data.spi.Consumer;
import org.ifinalframework.data.spi.Function;
import org.ifinalframework.data.spi.QueryConsumer;
import org.ifinalframework.data.spi.SpiAction;
import org.ifinalframework.data.spi.UpdateConsumer;
import org.ifinalframework.data.spi.UpdateFunction;
import org.ifinalframework.data.spi.UpdateProperty;
import org.ifinalframework.util.Proxies;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * DefaultUpdateDomainActionDispatcherFactory
 *
 * @author iimik
 * @since 1.5.6
 **/
public class DefaultUpdateDomainActionDispatcherFactory implements UpdateDomainActionDispatcherFactory {

    @Override
    public <K extends Serializable, T extends IEntity<K>> UpdateAction create(String property, Repository<K, T> repository, List<UpdateProperty<T>> updateProperties) {

        UpdateFunction updateFunction = updateProperties.stream()
                .filter(it -> it instanceof UpdateFunction<?, ?, ?, ?, ?>)
                .map(it -> (UpdateFunction<?, ?, ?, ?, ?>) it)
                .findFirst()
                .orElse(null);

        if (Objects.isNull(updateFunction)) {
            if (Objects.isNull(property)) {
                updateFunction = new DefaultUpdateFunction<>(repository);
            } else {
                updateFunction = new DefaultUpdatePropertyFunction(property, repository);
            }
        }

        final List<QueryConsumer> preQueryConsumers = new LinkedList<>();
        final List<BiValidator> preUpdateValidator = new LinkedList<>();
        final List<Consumer> preConsumers = new LinkedList<>();
        final List<UpdateConsumer> preUpdateConsumers = new LinkedList<>();
        final List<UpdateConsumer> postUpdateConsumers = new LinkedList<>();
        final List<Consumer> postConsumers = new LinkedList<>();
        final List<BiConsumer> postQueryConsumers = new LinkedList<>();
        final List<Function> postQueryFunctions = new LinkedList<>();
        final List<BiAfterThrowingConsumer> biAfterThrowingConsumers = new LinkedList<>();
        final List<BiAfterReturningConsumer> biAfterReturningConsumers = new LinkedList<>();
        final List<AfterConsumer> afterConsumers = new LinkedList<>();

        for (UpdateProperty<T> updateProperty : updateProperties) {

            final Class<?> targetClass = AopUtils.getTargetClass(updateProperty);
            final String simpleName = targetClass.getSimpleName();
            final boolean isPre = simpleName.contains("Pre");
            final boolean isPost = simpleName.contains("Post");


            if (updateProperty instanceof QueryConsumer preQueryConsumer) {
                preQueryConsumers.add(preQueryConsumer);
            } else if (updateProperty instanceof BiValidator<?, ?, ?> biValidator) {
                if (isPre) {
                    preUpdateValidator.add(biValidator);
                }
            } else if (updateProperty instanceof Consumer<?, ?> consumer) {
                if (isPre) {
                    preConsumers.add(consumer);
                } else if (isPost) {
                    postConsumers.add(consumer);
                }
            } else if (updateProperty instanceof UpdateConsumer<?, ?, ?> updateConsumer) {
                if (isPre) {
                    preUpdateConsumers.add(updateConsumer);
                } else if (isPost) {
                    postUpdateConsumers.add(updateConsumer);
                }
            } else if (updateProperty instanceof BiConsumer<?, ?, ?> biConsumer) {
                if (isPost) {
                    postQueryConsumers.add(biConsumer);
                }
            } else if (updateProperty instanceof Function<?, ?, ?> function) {
                if (isPost) {
                    postQueryFunctions.add(function);
                }
            } else if (updateProperty instanceof BiAfterThrowingConsumer<?, ?, ?> biAfterThrowingConsumer) {
                biAfterThrowingConsumers.add(biAfterThrowingConsumer);
            } else if (updateProperty instanceof BiAfterReturningConsumer<?, ?, ?> biAfterReturningConsumer) {
                biAfterReturningConsumers.add(biAfterReturningConsumer);
            } else if (updateProperty instanceof AfterConsumer<?, ?, ?, ?, ?> afterConsumer) {
                afterConsumers.add(afterConsumer);
            }
        }

        final UpdateDomainActionDispatcher dispatcher
                = new UpdateDomainActionDispatcher<>(SpiAction.UPDATE, repository, updateFunction);

        dispatcher.setPreQueryConsumer(Proxies.composite(QueryConsumer.class, preQueryConsumers));
        dispatcher.setPreUpdateValidator(Proxies.composite(BiValidator.class, preUpdateValidator));
        dispatcher.setPreConsumer(Proxies.composite(Consumer.class, preConsumers));
        dispatcher.setPreUpdateConsumer(Proxies.composite(UpdateConsumer.class, preUpdateConsumers));
        dispatcher.setPostUpdateConsumer(Proxies.composite(UpdateConsumer.class, postUpdateConsumers));
        dispatcher.setPostConsumer(Proxies.composite(Consumer.class, postConsumers));
        dispatcher.setPostQueryConsumer(Proxies.composite(BiConsumer.class, postQueryConsumers));
        dispatcher.setPostQueryFunction(Proxies.composite(Function.class, postQueryFunctions));
        dispatcher.setBiAfterThrowingConsumer(Proxies.composite(BiAfterThrowingConsumer.class, biAfterThrowingConsumers));
        dispatcher.setBiAfterReturningConsumer(Proxies.composite(BiAfterReturningConsumer.class, biAfterReturningConsumers));
        dispatcher.setAfterConsumer(Proxies.composite(AfterConsumer.class, afterConsumers));

        return dispatcher;
    }
}
