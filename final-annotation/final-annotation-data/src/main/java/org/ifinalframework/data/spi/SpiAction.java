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

package org.ifinalframework.data.spi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * SpiAction.
 *
 * @author iimik
 * @version 1.4.3
 * @since 1.4.3
 */
@Getter
public enum SpiAction {
    CREATE("create", "Insert"),
    DELETE("delete", "Delete"),
    UPDATE("update", "Update"),
    UPDATE_YN("yn", "UpdateYn"),
    UPDATE_LOCKED("lock", "UpdateLocked"),
    UPDATE_STATUS("status", "UpdateStatus"),
    UPDATE_AUDIT_STATUS("audit", "UpdateAuditStatus"),
    LIST("list", "List", "Query"),

    DETAIL("detail", "Detail", "Query"),
    COUNT("count", "CountQuery", "Query"),

    EXPORT("export", "ExportQuery", "ListQuery", "Query"), SORT("sort", "Sort");

    private final String authority;
    private final String[] values;

    SpiAction(String authority, String... values) {
        this.authority = authority;
        this.values = values;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Advice {
        PRE("Pre"), POST("Post"), AFTER("After"), AFTER_THROWING("AfterThrowing"), AFTER_RETURNING("AfterReturning");

        private final String value;

    }

    public enum Type {
        CREATE,
        SORT,
        DELETE_BY_QUERY, DELETE_BY_ID,
        LIST_BY_QUERY,
        EXPORT_BY_QUERY,
        DETAIL_BY_QUERY, DETAIL_BY_ID,
        UPDATE_BY_ID,
        UPDATE_YN_BY_ID,
        UPDATE_LOCKED_BY_ID,
        UPDATE_STATUS_BY_ID,
        UPDATE_AUDIT_STATUS_BY_ID,
        COUNT_BY_QUERY
    }
}
