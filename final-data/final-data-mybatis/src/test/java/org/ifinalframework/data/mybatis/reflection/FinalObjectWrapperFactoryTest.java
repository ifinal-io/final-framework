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

package org.ifinalframework.data.mybatis.reflection;

import org.ifinalframework.data.mybatis.reflection.wrapper.MultiBeanWrapper;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.CollectionWrapper;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FinalObjectWrapperFactoryTest.
 *
 * @author iimik
 * @version 1.4.0
 * @since 1.4.0
 */
class FinalObjectWrapperFactoryTest {

    private final ObjectWrapperFactory factory = new FinalObjectWrapperFactory();

    @Test
    void hasWrapperFor() {
        assertTrue(factory.hasWrapperFor(null));
        assertTrue(factory.hasWrapperFor(Collections.emptyList()));
        assertTrue(factory.hasWrapperFor(Collections.emptyMap()));
        assertTrue(factory.hasWrapperFor(new Object()));
    }

    @Test
    void getWrapperFor() {
        MetaObject metaObject = MetaObject.forObject(new Object(), new DefaultObjectFactory(), new DefaultObjectWrapperFactory(),
                new DefaultReflectorFactory());
        assertInstanceOf(MapWrapper.class, factory.getWrapperFor(metaObject, Collections.emptyMap()));
        assertInstanceOf(CollectionWrapper.class, factory.getWrapperFor(metaObject, Collections.emptyList()));
        assertInstanceOf(MultiBeanWrapper.class, factory.getWrapperFor(metaObject, new Object()));
    }
}