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

package org.ifinalframework.data.beans;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;

import org.ifinalframework.data.query.BetweenValue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * BetweenValueBeanTypeDescriptorFactoryTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
class BetweenValueBeanTypeDescriptorFactoryTest {

    private BetweenValueBeanTypeDescriptorFactory beanTypeDescriptorFactory = new BetweenValueBeanTypeDescriptorFactory();

    @Test
    void create() {
        final ResolvableType resolvableType = ResolvableType.forClassWithGenerics(BetweenValue.class, Integer.class);
        final BetweenValue betweenValue = beanTypeDescriptorFactory.create(Integer.class, new TypeDescriptor(resolvableType, null, null));
        betweenValue.setMin(1);
        betweenValue.setMax(2);
        Assertions.assertEquals(1, betweenValue.getMin());
        Assertions.assertEquals(2, betweenValue.getMax());
    }

    @Test
    void longBetweenValue() {
        final ResolvableType resolvableType = ResolvableType.forClassWithGenerics(BetweenValue.class, Long.class);
        final BetweenValue betweenValue = beanTypeDescriptorFactory.create(Long.class, new TypeDescriptor(resolvableType, null, null));
        betweenValue.setMin(1L);
        betweenValue.setMax(2L);
        Assertions.assertEquals(1L, betweenValue.getMin());
        Assertions.assertEquals(2L, betweenValue.getMax());
    }

    @Test
    void localDateTimeBetweenValue() {
        final ResolvableType resolvableType = ResolvableType.forClassWithGenerics(BetweenValue.class, LocalDateTime.class);
        final BetweenValue betweenValue = beanTypeDescriptorFactory.create(LocalDateTime.class,
                new TypeDescriptor(resolvableType, null, null));

        betweenValue.setMin(LocalDate.now().atStartOfDay());
        betweenValue.setMax(LocalDate.now().atTime(23, 59, 59));
        Assertions.assertEquals(LocalDate.now().atStartOfDay(), betweenValue.getMin());
        Assertions.assertEquals(LocalDate.now().atTime(23, 59, 59), betweenValue.getMax());
    }

    @Test
    void localDateBetweenValue() {
        final ResolvableType resolvableType = ResolvableType.forClassWithGenerics(BetweenValue.class, LocalDate.class);
        final BetweenValue betweenValue = beanTypeDescriptorFactory.create(LocalDate.class, new TypeDescriptor(resolvableType, null, null));

        betweenValue.setMin(LocalDate.now());
        betweenValue.setMax(LocalDate.now());
        Assertions.assertEquals(LocalDate.now(), betweenValue.getMin());
        Assertions.assertEquals(LocalDate.now(), betweenValue.getMax());
    }

    @Test
    void dateBetweenValue() {
        final ResolvableType resolvableType = ResolvableType.forClassWithGenerics(BetweenValue.class, Date.class);
        final BetweenValue betweenValue = beanTypeDescriptorFactory.create(Date.class, new TypeDescriptor(resolvableType, null, null));

        betweenValue.setMin(new Date(LocalDate.now().toEpochDay()));
        betweenValue.setMax(new Date(LocalDate.now().toEpochDay()));
        Assertions.assertEquals(new Date(LocalDate.now().toEpochDay()), betweenValue.getMin());
        Assertions.assertEquals(new Date(LocalDate.now().toEpochDay()), betweenValue.getMax());
    }

}