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

package org.ifinalframework.context.expression;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ObjectUtils;

import org.ifinalframework.json.Json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SpelTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class SpelTest {

    @Test
    void primary() {
        assertEquals(1, Spel.getValue("#{1}"));
        assertEquals(2, Spel.getValue("#{1+1}"));
        assertEquals("11", Spel.getValue("#{1+'1'}"));
    }


    @Test
    void stand() {
        Person person = new Person("xiaoMing", 12, new Date(), null);
        assertEquals(person.getName(), Spel.getValue("#{name}", person));
        assertEquals(person.getAge(), Spel.getValue("#{age}", person));
        logger.info("name={}", Spel.getValue("#{name}", person));
        logger.info("date={}", Spel.getValue("#{new java.text.SimpleDateFormat('yyyy-MM-dd').format(date)}", person));

        logger.info("creator.name={}", Spel.getValue("#{creator.name}", person));
        Spel.setValue("creator.name", person, "123321");
        logger.info("creator.name={}", Spel.getValue("#{creator.name}", person));

        Spel.setValue("name", person, "haha");
        assertEquals("haha", Spel.getValue("#{name}", person));
        logger.info("name={}", Spel.getValue("#{name}", person));

        Map<String, Object> params = new HashMap<>();
        params.put("name", "xiaoMing");
        params.put("age", 12);
        logger.info("name={}", Spel.getValue("#{['name']}", params));

        Map<String, Object> ext = new HashMap<>();
        ext.put("date", new Date());
        ext.put("name", "po");

        params.put("ext", ext);

        StandardEvaluationContext context = new StandardEvaluationContext(params);
        context.addPropertyAccessor(new MapAccessor());

        Spel.setValue("ext", context, new HashMap<>());
        Spel.setValue("ext.name", context, "aaaa");

        logger.info("ext.name={}", Spel.getValue("#{ext.name}", context));
        final String expression = "#{ext.containsKey('date') ? new java.text.SimpleDateFormat('yyyy-MM-dd').format(ext.date) : ''}";
        logger.info("date={}", Spel.getValue(expression, context));

    }

    @Test
    void getValueFromMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "xiaoMing");
        //        params.put("age", 12);
        assertEquals(params.get("name"), Spel.getValue("#{name}", params));
        assertEquals(params.get("age"), Spel.getValue("#{age}", params));

        StandardEvaluationContext context = new StandardEvaluationContext(params);
        context.addPropertyAccessor(new MapAccessor());

        Spel.setValue("ext", context, new HashMap<>());
        Spel.setValue("ext.name", context, "aaaa");
        logger.info("ext.name={}", Spel.getValue("#{ext.name}", params));

    }

    @Test
    void withParserContext() {
        Object value = Spel.getValue("hello #{3+4}");
        logger.info("hello #{3+4}={}", value);
        assertEquals("hello 7", value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"#{['data'][0]['shopCode']}"})
    void test2(String expression) {
        final String json = """
                {
                    "data":[
                        {
                            "id":20936066,
                            "yn":1,
                            "mart":"bjwm",
                            "skuId":100929389,
                            "status":14,
                            "barCode":"6971460180066",
                            "bizType":0,
                            "created":1590484875000,
                            "storeId":13558,
                            "baseUnit":"EA",
                            "createId":"sap",
                            "modified":1616489347000,
                            "shopCode":"8632",
                            "shopName":"明光村店(筹备中)",
                            "shopType":"S",
                            "statusCn":"4",
                            "updateId":"郑涛",
                            "customize":{
                                "consignForSale":false,
                                "existOrderUnit":true
                            },
                            "goodsCode":"926053",
                            "goodsName":"微波鲜玉米",
                            "orderUnit":"EA",
                            "deliveryType":1,
                            "purchaseUnit":"EA",
                            "supplierCode":"DC15",
                            "supplierName":"大兴冷冻冷藏日配库",
                            "categoryCode0":"P20",
                            "categoryCode1":"UF2",
                            "categoryCode2":"285-001",
                            "outSupplierCode":"334310",
                            "outSupplierName":"小丁家（北京）农业科技有限公司"
                        }
                    ],
                    "mart":"bjwm",
                    "groupId":1142,
                    "venderId":1,
                    "timestamp":1619911098680,
                    "storeCreditCode":""
                }
                """;

        Object object = Json.toObject(json);

        Object value = Spel.getValue(expression, object);

        logger.info("{}={}", expression, value);

    }

    @Test
    void equals() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "xiaoMing");
        map.put("age", 18);
        Person person = new Person("xiaoMing", 12, null, null);

        List<String> expressions = Arrays.asList("#{name}", "#{age}");

        boolean equals = Spel.equals(map, person, expressions, new Spel.CompareListener() {
            @Override
            public void onCompare(String expression, Object leftValue, Object rightValue, boolean equals) {
                logger.info("expression={},leftValue={},rightValue={},equals={}", expression, leftValue, rightValue, equals);
            }
        });
        logger.info("equals={}", equals);

    }

    @Test
    void longTest() {
        Long a = new Long(1001071088L);
        Long b = 1001071088L;
        assertEquals(a, b);
        assertTrue(ObjectUtils.nullSafeEquals(a, b));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Person {

        private String name;

        private Integer age;

        private Date date;

        private Person creator;

    }

}
