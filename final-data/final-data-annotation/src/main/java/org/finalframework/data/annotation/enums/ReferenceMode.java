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

package org.finalframework.data.annotation.enums;

/**
 * 引用模式
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-07 13:38:11
 * @since 1.0
 */
public enum ReferenceMode {
    /**
     * 简单模式，{@literal id} 引用简写为引用属性名
     * <pre>
     *     <code>
     *          Bean.id ==> bean
     *     </code>
     * </pre>
     */
    SIMPLE,
    /**
     * 典范模式，与其他引用一样，{@literal id} 将与引用属性拼接
     * <pre>
     *     <code>
     *         Bean.id ==> beanId
     *     </code>
     * </pre>
     */
    CANONICAL
}
