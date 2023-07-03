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

package org.ifinalframework.web.servlet.response.advice;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.server.ServletServerHttpRequest;

import org.ifinalframework.core.result.Column;
import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.web.servlet.response.advice.ResultColumnResponseBodyAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * ResultColumnResponseBodyAdviceTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class ResultColumnResponseBodyAdviceTest {

    @Mock
    private ServletServerHttpRequest servletServerHttpRequest;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private ResultColumnResponseBodyAdvice resultColumnResponseBodyAdvice;

    //    @Test
    void beforeBodyWrite() {

        when(servletServerHttpRequest.getServletRequest()).thenReturn(httpServletRequest);

        List<Column> columns = Arrays.asList(
                new Column("name", "姓名", "#{name}"),
                new Column("age", "年龄", "#{age}")
        );
        when(httpServletRequest.getAttribute(anyString())).thenReturn(columns);

        List<Person> people = Arrays.asList(
                new Person("xiaoMing", 12),
                new Person("xiaoHong", 18)
        );

        Result<List<?>> result = R.success(people);

        resultColumnResponseBodyAdvice.beforeBodyWrite(result, null, null, null, servletServerHttpRequest, null);

        Assertions.assertNotNull(result.getHeader());

        Assertions.assertTrue(result.getData().get(0) instanceof Map);

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Person {

        private String name;

        private Integer age;

    }

}
