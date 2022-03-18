/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.context.expression;

import org.springframework.expression.spel.support.StandardEvaluationContext;

import org.ifinalframework.core.result.Column;
import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.json.Json;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * ResultWithHeaderTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ResultWithHeaderTest {

    @Test
    void test() {
        List<Column> header = Arrays.asList(
            new Column("name", "姓名", "#{name}"),
            new Column("age", "年龄", "<span class=\"badge badge-danger\">#{age}岁</span>")
        );

        List<Person> list = Arrays.asList(
            new Person("xiaoMing", 12),
            new Person("xiaoHong", 18)
        );

        List<Map<String, Object>> data = list.stream()
            .map(it -> {
                Map<String, Object> item = new HashMap<>();
                StandardEvaluationContext context = new StandardEvaluationContext(it);

                for (final Column column : header) {
                    item.put(column.getKey(), Spel.getValue(column.getValue(), context));
                }

                return item;
            }).collect(Collectors.toList());

        Result<List<Map<String, Object>>> result = R.success(data);
        result.setHeader(header);

        logger.info(Json.toJson(result));

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Person implements Serializable {

        private String name;

        private Integer age;

    }

}
