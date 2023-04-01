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

package org.ifinalframework.validation.beanvalidation;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

import org.ifinalframework.validation.ValidationGroupsProvider;

import org.aopalliance.intercept.MethodInvocation;

/**
 * FinalMethodValidationInterceptor.
 *
 * @author ilikly
 * @version 1.5.0
 * @since 1.5.0
 */
public class FinalMethodValidationInterceptor extends MethodValidationInterceptor {
    private final ValidationGroupsProvider validationGroupsProvider;

    public FinalMethodValidationInterceptor(ValidationGroupsProvider validationGroupsProvider) {
        super();
        this.validationGroupsProvider = validationGroupsProvider;
    }

    public FinalMethodValidationInterceptor(ValidatorFactory validatorFactory, ValidationGroupsProvider validationGroupsProvider) {
        super(validatorFactory);
        this.validationGroupsProvider = validationGroupsProvider;
    }

    public FinalMethodValidationInterceptor(Validator validator, ValidationGroupsProvider validationGroupsProvider) {
        super(validator);
        this.validationGroupsProvider = validationGroupsProvider;
    }

    @Override
    @NonNull
    protected Class<?>[] determineValidationGroups(@NonNull MethodInvocation invocation) {

        final Class<?>[] determineValidationGroups = super.determineValidationGroups(invocation);
        final List<Class<?>> validationGroups = validationGroupsProvider.getValidationGroups(invocation.getMethod(), invocation.getThis(), invocation.getArguments());
        if (CollectionUtils.isEmpty(validationGroups)) {
            return determineValidationGroups;
        }

        final List<Class<?>> groups = new ArrayList<>(determineValidationGroups.length + validationGroups.size());
        groups.addAll(Arrays.asList(determineValidationGroups));
        groups.addAll(validationGroups);

        return ClassUtils.toClassArray(groups);
    }
}
