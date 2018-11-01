package cn.com.likly.finalframework.data.json.jackson;

import cn.com.likly.finalframework.data.json.Json;
import cn.com.likly.finalframework.data.json.JsonBean;
import cn.com.likly.finalframework.data.json.JsonRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class JacksonJsonServiceTest {

    @Before
    public void setUp() {
        JacksonJsonService jsonService = new JacksonJsonService();
        jsonService.setObjectMapper(new ObjectMapper());
        JsonRegistry.getInstance().registerJsonService(jsonService);

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
