package com.ilikly.finalframework.data.json.gson;

import com.google.gson.Gson;
import com.ilikly.finalframework.data.json.JsonBean;
import com.ilikly.finalframework.json.Json;
import com.ilikly.finalframework.json.JsonRegistry;
import com.ilikly.finalframework.json.TypeReference;
import com.ilikly.finalframework.json.gson.GsonJsonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-02 11:36
 * @since 1.0
 */
@Slf4j
@SuppressWarnings("all")
public class GsonJsonServiceTest {
    @Before
    public void setUp() {
        GsonJsonService jsonService = new GsonJsonService();
        jsonService.setGson(new Gson());
        JsonRegistry.getInstance().registerJsonService(jsonService);
    }

    @Test
    public void testToJson() {
        final JsonBean bean = new JsonBean().setName("Jack").setAge(20);
        logger.info("bean={}", Json.toJson(bean));
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
        List<Integer> list = Json.parse(json, List.class, Integer.class);
        logger.info("list={}", list);
        //parse to set
        Set<Integer> set = Json.parse(json, Set.class, int.class);
        logger.info("set={}", set);

        final String beanArray = Json.toJson(Arrays.asList(
                new JsonBean().setName("jack").setAge(11),
                new JsonBean().setName("jack").setAge(11),
                new JsonBean().setName("jack").setAge(11)
        ));

        List<JsonBean> beanList = Json.parse(beanArray, List.class, JsonBean.class);
        logger.info("beanList={}", beanList);


    }


}
