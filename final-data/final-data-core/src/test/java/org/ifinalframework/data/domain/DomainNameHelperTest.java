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

import org.ifinalframework.core.IView;
import org.ifinalframework.entity.DomainEntity;
import org.ifinalframework.entity.sub.SubDomainEntity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DomainNameHelperTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
class DomainNameHelperTest {


    @Test
    void test() {

        // mapper
        assertEquals("DomainEntityMapper", DomainNameHelper.mapperName(DomainEntity.class));
        assertEquals("SubDomainEntityMapper", DomainNameHelper.mapperName(SubDomainEntity.class));

        assertEquals("org.ifinalframework.repository.mapper", DomainNameHelper.mapperPackage(DomainEntity.class));
        assertEquals("org.ifinalframework.repository.sub.mapper", DomainNameHelper.mapperPackage(SubDomainEntity.class));


        // query entity
        assertEquals("QDomainEntity", DomainNameHelper.queryEntityName(DomainEntity.class));
        assertEquals("QSubDomainEntity", DomainNameHelper.queryEntityName(SubDomainEntity.class));
        assertEquals("org.ifinalframework.repository.query", DomainNameHelper.queryEntityPackage(DomainEntity.class));
        assertEquals("org.ifinalframework.repository.sub.query", DomainNameHelper.queryEntityPackage(SubDomainEntity.class));

        // domain query
        assertEquals("DomainEntityQuery", DomainNameHelper.domainQueryName(DomainEntity.class));
        assertEquals("SubDomainEntityQuery", DomainNameHelper.domainQueryName(SubDomainEntity.class));

        assertEquals("DomainEntityListQuery", DomainNameHelper.domainQueryName(DomainEntity.class, IView.List.class));
        assertEquals("DomainEntityDetailQuery", DomainNameHelper.domainQueryName(DomainEntity.class, IView.Detail.class));

        assertEquals("org.ifinalframework.domain.query", DomainNameHelper.domainQueryPackage(DomainEntity.class));
        assertEquals("org.ifinalframework.domain.sub.query", DomainNameHelper.domainQueryPackage(SubDomainEntity.class));

    }

}