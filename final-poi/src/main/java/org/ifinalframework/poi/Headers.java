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

package org.ifinalframework.poi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@code Excel}表头
 *
 * @author iimik
 * @version 1.2.4
 **/
@RequiredArgsConstructor
public final class Headers {
    /**
     * 标题行索引
     */
    @Getter
    private final int row;
    /**
     * 表头下标标题索引
     * <ul>
     *     <li>key: 标题</li>
     *     <li>value: 索引</li>
     * </ul>
     *
     * @see #indexHeaderMap
     */
    private final Map<String, Integer> headerIndexMap = new LinkedHashMap<>();
    /**
     * 表头下标标题索引
     * <ul>
     *     <li>key: 下标</li>
     *     <li>value：标题</li>
     * </ul>
     *
     * @see #headerIndexMap
     */
    private final Map<Integer, String> indexHeaderMap = new LinkedHashMap<>();

    public void addHeader(Integer index, String header) {
        headerIndexMap.put(header, index);
        indexHeaderMap.put(index, header);
    }

    public Integer getHeaderIndex(String header) {
        return headerIndexMap.get(header);
    }

    public String getIndexHeader(Integer index) {
        return indexHeaderMap.get(index);
    }

    public void foreach(BiConsumer<String, Integer> action) {
        headerIndexMap.forEach(action);
    }

    public boolean hasHeader(String header) {
        return headerIndexMap.containsKey(header);
    }

    public int size() {
        return headerIndexMap.size();
    }
}
