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

package org.finalframework.json;

import junit.framework.TestCase;
import org.finalframework.annotation.data.YN;
import org.finalframework.json.jackson.JacksonJsonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-22 22:56:19
 * @since 1.0
 */
 class JsonRegistryTest extends TestCase {

    @Mock
    private JsonRegistry jsonRegistry;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetJsonService() {
        //given

        //when
        when(jsonRegistry.getJsonService()).thenReturn(new JacksonJsonService());
        //then
        System.out.println(Json.toJson(YN.class));
        System.out.printf("");
    }
}