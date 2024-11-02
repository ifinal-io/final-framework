/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.data.mapping.converter;

import org.springframework.lang.Nullable;

import org.ifinalframework.Configuration;
import org.ifinalframework.data.annotation.NameConverter;
import org.ifinalframework.util.Asserts;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public final class NameConverterRegistry {

    private static final String FINAL_NAME_CONVERTER_TABLE_CONVERTER = "final.data.name-converter.table-converter";

    private static final String FINAL_NAME_CONVERTER_COLUMN_CONVERTER = "final.data.name-converter.column-converter";

    private static final NameConverterRegistry instance = new NameConverterRegistry();

    private final NameConverter defaultNameConverter = new CameHump2UnderlineNameConverter();

    private NameConverter tableNameConverter = defaultNameConverter;

    private NameConverter columnNameConverter = defaultNameConverter;

    private NameConverterRegistry() {
        reload();
    }

    public static NameConverterRegistry getInstance() {
        return instance;
    }

    public void reload() {
        final Configuration configuration = Configuration.getInstance();
        initTableConverter(configuration.getString(FINAL_NAME_CONVERTER_TABLE_CONVERTER, null));
        initNameConverter(configuration.getString(FINAL_NAME_CONVERTER_COLUMN_CONVERTER, null));
    }

    private void initTableConverter(final @Nullable String tableNameConverter) {

        if (Asserts.isEmpty(tableNameConverter)) {
            return;
        }
        try {
            this.tableNameConverter = (NameConverter) Class.forName(tableNameConverter).getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void initNameConverter(final @Nullable String columnNameConverter) {

        if (Asserts.isEmpty(columnNameConverter)) {
            return;
        }
        try {
            this.columnNameConverter = (NameConverter) Class.forName(columnNameConverter).getConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public NameConverter getTableNameConverter() {
        return tableNameConverter;
    }

    public void setTableConverter(final NameConverter tableNameConverter) {

        this.tableNameConverter = tableNameConverter;
    }

    public NameConverter getColumnNameConverter() {
        return columnNameConverter;
    }

    public void setColumnConverter(final NameConverter columnNameConverter) {

        this.columnNameConverter = columnNameConverter;
    }

}
