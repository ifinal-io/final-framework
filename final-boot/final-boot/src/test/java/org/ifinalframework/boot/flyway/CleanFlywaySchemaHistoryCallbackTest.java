/*
 * Copyright 2020-2024 the original author or authors.
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

package org.ifinalframework.boot.flyway;

import org.flywaydb.core.api.callback.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CleanFlywaySchemaHistoryCallbackTest
 *
 * @author iimik
 * @since 1.6.0
 **/
@ExtendWith(MockitoExtension.class)
class CleanFlywaySchemaHistoryCallbackTest {

    @InjectMocks
    private CleanFlywaySchemaHistoryCallback callback;

    @ParameterizedTest
    @EnumSource(value = Event.class)
    void supports(Event event) {
        final boolean supports = callback.supports(event, null);
        Assertions.assertEquals(Event.AFTER_MIGRATE_OPERATION_FINISH == event, supports);
    }
}