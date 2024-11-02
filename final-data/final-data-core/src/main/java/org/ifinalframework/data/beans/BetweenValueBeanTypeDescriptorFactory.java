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

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.TypeDescriptor;

import org.ifinalframework.auto.spring.factory.annotation.SpringFactory;
import org.ifinalframework.beans.BeanTypeDescriptorFactory;
import org.ifinalframework.data.query.BetweenValue;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import lombok.SneakyThrows;

/**
 * {@link BetweenValue}创建工厂。
 *
 * <h4>支持以下类型的{@link BetweenValue}</h4>
 * <ul>
 *     <li>{@link LongBetweenValue}</li>
 *     <li>{@link IntegerBetweenValue}</li>
 *     <li>{@link DateBetweenValue}</li>
 *     <li>{@link LocalDateBetweenValue}</li>
 *     <li>{@link LocalDateTimeBetweenValue}</li>
 * </ul>
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@SpringFactory(BeanTypeDescriptorFactory.class)
public class BetweenValueBeanTypeDescriptorFactory implements BeanTypeDescriptorFactory<BetweenValue<?>> {

    private final Map<Class<?>, Class<? extends BetweenValue<?>>> cache = new LinkedHashMap<>(8);

    @SneakyThrows
    public BetweenValueBeanTypeDescriptorFactory() {
        cache.put(Integer.class, IntegerBetweenValue.class);
        cache.put(Long.class, LongBetweenValue.class);
        cache.put(LocalDate.class, LocalDateBetweenValue.class);
        cache.put(LocalDateTime.class, LocalDateTimeBetweenValue.class);
        cache.put(Date.class, DateBetweenValue.class);

    }

    @Override
    public boolean support(Class<?> type, TypeDescriptor typeDescriptor) {
        return BetweenValue.class.equals(type);
    }

    @Override
    public BetweenValue<?> create(Class<?> type, TypeDescriptor typeDescriptor) {

        final Type clazz = typeDescriptor.getResolvableType().getGeneric().getType();
        final Class<? extends BetweenValue<?>> targetClass = cache.get(clazz);

        if (Objects.nonNull(targetClass)) {
            return BeanUtils.instantiateClass(targetClass);
        }

        return new BetweenValue<>();
    }

    /**
     * LocalDateTimeBetweenValue.
     *
     * @author iimik
     * @version 1.4.2
     * @since 1.4.2
     */
    @SuppressWarnings("all")
    private static final class LocalDateTimeBetweenValue extends BetweenValue<LocalDateTime> {
        @Override
        public void setMax(LocalDateTime max) {
            super.setMax(max);
        }

        @Override
        public void setMin(LocalDateTime min) {
            super.setMin(min);
        }

        @Override
        public LocalDateTime getMax() {
            return super.getMax();
        }

        @Override
        public LocalDateTime getMin() {
            return super.getMin();
        }
    }

    @SuppressWarnings("all")
    private static final class LocalDateBetweenValue extends BetweenValue<LocalDate> {
        @Override
        public void setMax(LocalDate max) {
            super.setMax(max);
        }

        @Override
        public void setMin(LocalDate min) {
            super.setMin(min);
        }

        @Override
        public LocalDate getMax() {
            return super.getMax();
        }

        @Override
        public LocalDate getMin() {
            return super.getMin();
        }
    }

    @SuppressWarnings("all")
    private static final class DateBetweenValue extends BetweenValue<Date> {
        @Override
        public void setMax(Date max) {
            super.setMax(max);
        }

        @Override
        public void setMin(Date min) {
            super.setMin(min);
        }

        @Override
        public Date getMax() {
            return super.getMax();
        }

        @Override
        public Date getMin() {
            return super.getMin();
        }
    }


    /**
     * LongBetweenValue.
     *
     * @author iimik
     * @version 1.4.2
     * @since 1.4.2
     */
    @SuppressWarnings("all")
    private static final class LongBetweenValue extends BetweenValue<Long> {
        @Override
        public void setMin(Long min) {
            super.setMin(min);
        }

        @Override
        public void setMax(Long max) {
            super.setMax(max);
        }

        @Override
        public Long getMax() {
            return super.getMax();
        }

        @Override
        public Long getMin() {
            return super.getMin();
        }
    }

    @SuppressWarnings("all")
    private static final class IntegerBetweenValue extends BetweenValue<Integer> {
        @Override
        public void setMin(Integer min) {
            super.setMin(min);
        }

        @Override
        public void setMax(Integer max) {
            super.setMax(max);
        }

        @Override
        public Integer getMax() {
            return super.getMax();
        }

        @Override
        public Integer getMin() {
            return super.getMin();
        }
    }

}
