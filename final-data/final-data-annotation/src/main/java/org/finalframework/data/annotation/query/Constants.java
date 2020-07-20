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

package org.finalframework.data.annotation.query;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-18 01:19:45
 * @since 1.0
 */
public interface Constants {
    String PREFIX = "<![CDATA[";
    String SUFFIX = "]]>";
    String AND_OR = "${andOr} ";
    String COLUMN = "${column}";
    String VALUE = "${value}";
    String MIN = "${value}.min";
    String MAX = "${value}.max";
    String JAVA_TYPE = "#if($javaType),javaType=$!{javaType.canonicalName}#end";
    String TYPE_HANDLER = "#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end";
    String DEFAULT_VALUE = "#{" + VALUE + JAVA_TYPE + TYPE_HANDLER + "}";
    String DEFAULT_MIN = "#{" + MIN + JAVA_TYPE + TYPE_HANDLER + "}";
    String DEFAULT_MAX = "#{" + MAX + JAVA_TYPE + TYPE_HANDLER + "}";
    String EQUAL = PREFIX + AND_OR + COLUMN + " = " + DEFAULT_VALUE + SUFFIX;
    String NOT_EQUAL = PREFIX + AND_OR + COLUMN + " != " + DEFAULT_VALUE + SUFFIX;

}
