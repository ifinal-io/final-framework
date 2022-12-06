/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.beans;

import java.awt.*;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.BeanInfoFactory;

import org.ifinalframework.query.BetweenValue;

/**
 * BetweenValueBeanInfoFactory.
 *
 * @author ilikly
 * @version 1.4.2
 * @since 1.4.2
 */
public class BetweenValueBeanInfoFactory implements BeanInfoFactory {
    @Override
    public BeanInfo getBeanInfo(Class<?> beanClass) throws IntrospectionException {
        return beanClass.equals(BetweenValue.class) ? new BetweenValueBeanInfo(Introspector.getBeanInfo(beanClass)) : null;
    }

    class BetweenValueBeanInfo implements BeanInfo {
        private final BeanInfo delegate;
        private final Set<PropertyDescriptor> propertyDescriptors = new LinkedHashSet<>();

        public BetweenValueBeanInfo(BeanInfo delegate) {
            this.delegate = delegate;
        }

        @Override
        public BeanDescriptor getBeanDescriptor() {
            return delegate.getBeanDescriptor();
        }

        @Override
        public EventSetDescriptor[] getEventSetDescriptors() {
            return new EventSetDescriptor[0];
        }

        @Override
        public int getDefaultEventIndex() {
            return 0;
        }

        @Override
        public PropertyDescriptor[] getPropertyDescriptors() {
            return new PropertyDescriptor[0];
        }

        @Override
        public int getDefaultPropertyIndex() {
            return 0;
        }

        @Override
        public MethodDescriptor[] getMethodDescriptors() {
            return new MethodDescriptor[0];
        }

        @Override
        public BeanInfo[] getAdditionalBeanInfo() {
            return new BeanInfo[0];
        }

        @Override
        public Image getIcon(int iconKind) {
            return null;
        }
    }

    class BetweenValuePropertyDescriptor extends PropertyDescriptor{

    }

}


