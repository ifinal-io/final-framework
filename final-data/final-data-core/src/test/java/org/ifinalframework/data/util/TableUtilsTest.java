/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.util;

import org.ifinalframework.data.annotation.Table;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TableUtilsTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
class TableUtilsTest {

    private static class SimpleTable {
    }

    @Table("my_single_table")
    private static class SingleTable {
    }

    @Table({"table_1", "table_2"})
    private static class MultiTables {
    }

    @Test
    void getTable() {
        assertEquals("simple_table", TableUtils.getTable(SimpleTable.class));
        assertEquals("my_single_table", TableUtils.getTable(SingleTable.class));
        assertEquals(Arrays.asList("table_1", "table_2"), TableUtils.getTables(MultiTables.class));
    }
}