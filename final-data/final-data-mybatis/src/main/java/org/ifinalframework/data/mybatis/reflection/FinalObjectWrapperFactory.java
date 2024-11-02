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

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.CollectionWrapper;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Collection;
import java.util.Map;

/**
 * FinalObjectWrapperFactory.
 *
 * @author iimik
 * @version 1.3.3
 * @see MetaObject
 * @since 1.3.3
 */
public class FinalObjectWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object object) {
        return true;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {

        if (object instanceof Map) {
            return new MapWrapper(metaObject, (Map) object);
        } else if (object instanceof Collection) {
            return new CollectionWrapper(metaObject, (Collection<Object>) object);
        } else {
            return new MultiBeanWrapper(metaObject, object);
        }
    }
}


