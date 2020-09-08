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

package org.finalframework.cache.operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 17:35
 * @since 1.0
 */
public interface CacheOrder {

    int CACHE_INCREMENT = -200;
    int CACHE_VALUE = -180;
    int CACHE_LOCK = -160;
    int CACHE_DEL = -100;
    int CACHE_ABLE = -100;
    int CACHE_PUT = -100;

}
