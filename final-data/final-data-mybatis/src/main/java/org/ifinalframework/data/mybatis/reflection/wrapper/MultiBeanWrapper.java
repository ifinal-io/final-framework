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

package org.ifinalframework.data.mybatis.reflection.wrapper;

import org.springframework.util.StringUtils;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;

import java.util.List;
import java.util.Map;

/**
 * BeanWrapper.
 *
 * @author iimik
 * @version 1.3.3
 * @since 1.3.3
 */
public class MultiBeanWrapper extends BeanWrapper {

    private static final String ARRAY_DELIMITER = "][";

    public MultiBeanWrapper(MetaObject metaObject, Object object) {
        super(metaObject, object);
    }

    @Override
    protected Object getCollectionValue(PropertyTokenizer prop, Object collection) {
        if (collection instanceof Map) {
            return super.getCollectionValue(prop, collection);
        }

        String index = prop.getIndex();

        if (!index.contains(ARRAY_DELIMITER)) {
            return super.getCollectionValue(prop, collection);
        }

        String[] indexes = StringUtils.split(index, ARRAY_DELIMITER);

        Object value = collection;

        for (String item : indexes) {
            value = getCollectionValue(item, value);
        }

        return value;
    }


    private Object getCollectionValue(String index, Object collection) {
        int i = Integer.parseInt(index);
        if (collection instanceof List) {
            return ((List) collection).get(i);
        } else if (collection instanceof Object[]) {
            return ((Object[]) collection)[i];
        } else if (collection instanceof char[]) {
            return ((char[]) collection)[i];
        } else if (collection instanceof boolean[]) {
            return ((boolean[]) collection)[i];
        } else if (collection instanceof byte[]) {
            return ((byte[]) collection)[i];
        } else if (collection instanceof double[]) {
            return ((double[]) collection)[i];
        } else if (collection instanceof float[]) {
            return ((float[]) collection)[i];
        } else if (collection instanceof int[]) {
            return ((int[]) collection)[i];
        } else if (collection instanceof long[]) {
            return ((long[]) collection)[i];
        } else if (collection instanceof short[]) {
            return ((short[]) collection)[i];
        } else {
            throw new ReflectionException("The '" + index + "' property of " + collection + " is not a List or Array.");
        }
    }
}


