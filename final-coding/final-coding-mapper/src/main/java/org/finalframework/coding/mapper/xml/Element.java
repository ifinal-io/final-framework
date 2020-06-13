/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.coding.mapper.xml;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 19:09:48
 * @since 1.0
 */
public interface Element extends Comparable<Element> {
    default String name() {
        return type().getName();
    }

    ElementType type();

    @Override
    default int compareTo(Element o) {
        return type().order.compareTo(o.type().order);
    }

    enum ElementType implements Comparable<ElementType> {
        RESULT_MAP("resultMap", 0),
        ID_RESULT("id", 1),
        RESULT("result", 2),
        ASSOCIATION("association", 3);

        private final String name;
        private final Integer order;

        ElementType(String name, Integer order) {
            this.name = name;
            this.order = order;
        }

        public String getName() {
            return name;
        }


    }
}
