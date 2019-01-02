package com.ilikly.finalframework.data.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilikly.finalframework.data.json.JsonBean;
import com.ilikly.finalframework.json.Json;
import com.ilikly.finalframework.json.JsonRegistry;
import com.ilikly.finalframework.json.TypeReference;
import com.ilikly.finalframework.json.jackson.JacksonJsonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 22:52
 * @since 1.0
 */

@Slf4j
@SuppressWarnings("all")
public class JacksonJsonServiceTest {

    @Before
    public void setUp() {
        JacksonJsonService jsonService = new JacksonJsonService();
        jsonService.setObjectMapper(new ObjectMapper());
        JsonRegistry.getInstance().register(jsonService);

    }

    @Test
    public void testToJson() {
        final JsonBean bean = new JsonBean();
        bean.setName("xiaoMing");
        bean.setAge(20);
        logger.info("bean={}", bean);
        final String json = Json.toJson(bean);
        logger.info(json);

    }

    @Test
    public void testParseObject() {
        final JsonBean bean = new JsonBean();
        bean.setName("xiaoMing");
        bean.setAge(20);
        logger.info("bean={}", bean);
        final String json = Json.toJson(bean);
        logger.info(json);
        final JsonBean result = Json.parse(json, JsonBean.class);
        logger.info("result={}", result);
    }


    @Test
    public void testParseType() {
        final String json = Json.toJson(Arrays.asList(1, 2, 3, 2, 1));
        //parse to list
        List<Integer> list = Json.parse(json, new TypeReference<List<Integer>>() {
        }.getType());
        logger.info("list={}", list);
        //parse to set
        Set<Integer> set = Json.parse(json, new TypeReference<Set<Integer>>() {
        }.getType());
        logger.info("set={}", set);
    }

    @Test
    public void testParseCollection() {
        final String json = Json.toJson(Arrays.asList(1, 2, 3, 2, 1));
        //parse to list
        List<Integer> list = Json.parse(json, List.class, int.class);
        logger.info("list={}", list);
        //parse to set
        Set<Integer> set = Json.parse(json, Set.class, int.class);
        logger.info("set={}", set);
    }

}
