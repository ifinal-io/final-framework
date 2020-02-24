package org.finalframework.json.jackson;

import com.alibaba.fastjson.JSONObject;
import org.finalframework.data.entity.enums.YN;
import org.finalframework.json.Json;
import org.finalframework.json.JsonRegistry;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-16 22:38:47
 * @since 1.0
 */
public class JacksonJsonServiceTest {

    private String json;

    @Before
    public void setup() {
        JsonRegistry.getInstance().register(new JacksonJsonService());
        Map<String, Object> map = new HashMap<>();
        map.put("name", "XiaoMing");
        map.put("age", 12);
        Map<String,Object> properties = new HashMap<>();
        properties.put("add","中国");
        map.put("properties",properties);
        this.json = Json.toJson(map);

    }

    @Test
    public void toJson() {
        System.out.println(Json.toJson(YN.values()));
        JacksonBean bean = new JacksonBean();
        bean.setCreated(LocalDateTime.now());
        bean.setYn(YN.YES);
        System.out.println(Json.toJson(bean));
    }

    @Test
    public void toJSONObject() {
        JSONObject jsonObject = Json.toObject(json, JSONObject.class);
        assert jsonObject.getString("name").equals("XiaoMing");
    }

}