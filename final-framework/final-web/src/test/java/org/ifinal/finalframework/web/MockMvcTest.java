package org.ifinal.finalframework.web;

import org.ifinal.finalframework.annotation.data.YN;
import org.ifinal.finalframework.annotation.result.Result;
import org.ifinal.finalframework.json.Json;
import org.ifinal.finalframework.json.TypeReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MockMvcTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void hello() throws Exception {
        this.mvc.perform(get("/hello?word=finalframework"))
                .andExpect(status().isOk())
                .andExpect(new ResultMatcher() {
                    @Override
                    public void match(MvcResult result) throws Exception {
                        String json = result.getResponse().getContentAsString();
                        Result<String> value = Json.toObject(json, new TypeReference<Result<String>>() {
                        });
                        assertEquals("", value.getData(), "hello finalframework!");
                    }
                })
                .andDo(print());
    }

    @Test
    void helloEx() throws Exception {
        this.mvc.perform(get("/hello/ex?code=123&message=exception"))
                .andExpect(new ResultMatcher() {
                    @Override
                    public void match(MvcResult result) throws Exception {
                        String json = result.getResponse().getContentAsString();
                        Result<Void> value = Json.toObject(json, new TypeReference<Result<Void>>() {
                        });
                        assertEquals("", value.getStatus(), 123);
                    }
                })
                .andDo(print());
    }

    @Test
    void yn() throws Exception {
        this.mvc.perform(get("/enums/yn"))
//                .andExpect(status().isOk())
                .andExpect(new ResultMatcher() {
                    @Override
                    public void match(MvcResult result) throws Exception {
                        String json = result.getResponse().getContentAsString();
                        Result<ArrayList<YN>> value = Json.toObject(json, new TypeReference<Result<ArrayList<YN>>>() {
                        });
                        assertEquals("", value.getData().size(), YN.values().length);
                    }
                })
                .andDo(print());

    }
}
