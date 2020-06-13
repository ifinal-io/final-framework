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

package org.finalframework.data.mapping.converter;

import org.finalframework.core.Assert;
import org.finalframework.core.configuration.Configuration;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 17:52:29
 * @since 1.0
 */
public class NameConverterRegistry {

    private static final String FINAL_NAME_CONVERTER_TABLE_CONVERTER = "final.data.name-converter.table-converter";
    private static final String FINAL_NAME_CONVERTER_COLUMN_CONVERTER = "final.data.name-converter.column-converter";

    private static NameConverterRegistry instance = new NameConverterRegistry();

    private final NameConverter defaultNameConverter = new CameHump2UnderlineNameConverter();

    private NameConverter tableNameConverter = defaultNameConverter;
    private NameConverter columnNameConverter = defaultNameConverter;

    private NameConverterRegistry() {
        reload();
    }

    public void reload() {
        final Configuration configuration = Configuration.getInstance();
        initTableConverter(configuration.getString(FINAL_NAME_CONVERTER_TABLE_CONVERTER, null));
        initNameConverter(configuration.getString(FINAL_NAME_CONVERTER_COLUMN_CONVERTER, null));
    }

    private void initTableConverter(@Nullable String tableNameConverter) {
        if (Assert.isEmpty(tableNameConverter)) return;
        try {
            this.tableNameConverter = (NameConverter) Class.forName(tableNameConverter).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    private void initNameConverter(@Nullable String columnNameConverter) {
        if (Assert.isEmpty(columnNameConverter)) return;
        try {
            this.columnNameConverter = (NameConverter) Class.forName(columnNameConverter).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static NameConverterRegistry getInstance() {
        return instance;
    }

    public NameConverter getTableNameConverter() {
        return tableNameConverter;
    }

    public void setTableConverter(NameConverter tableNameConverter) {
        this.tableNameConverter = tableNameConverter;
    }

    public NameConverter getColumnNameConverter() {
        return columnNameConverter;
    }

    public void setColumnConverter(NameConverter columnNameConverter) {
        this.columnNameConverter = columnNameConverter;
    }
}
