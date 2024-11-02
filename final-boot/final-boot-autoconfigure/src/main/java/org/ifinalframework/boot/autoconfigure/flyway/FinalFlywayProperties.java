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

package org.ifinalframework.boot.autoconfigure.flyway;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * FlywayProperties.
 *
 * @author iimik
 * @version 1.5.0
 * @see org.springframework.boot.autoconfigure.flyway.FlywayProperties
 * @since 1.5.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "spring.flyway")
public class FinalFlywayProperties {

    /**
     * @see org.flywaydb.core.api.callback.Event#AFTER_MIGRATE_OPERATION_FINISH
     */
    private boolean cleanSchemaHistoryOnAfterMigrateOperationFinish = false;

    private String cleanSchemaHistorySql = "TRUNCATE TABLE flyway_schema_history;";
}
