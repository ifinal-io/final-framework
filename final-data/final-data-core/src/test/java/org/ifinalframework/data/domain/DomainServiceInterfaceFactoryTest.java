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

import org.ifinalframework.data.annotation.AbsEntity;
import org.ifinalframework.java.JadDriver;

import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * DomainServiceInterfaceFactoryTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Slf4j
class DomainServiceInterfaceFactoryTest {

    @Test
    @SneakyThrows
    void create() {
        final Class<? extends DomainService> domainServiceClass = new DomainServiceInterfaceFactory().create(Long.class, AbsEntity.class);
        final String jad = new JadDriver().jad(domainServiceClass);
        logger.info(jad);
    }
}