package org.finalframework.web.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/12 11:21:33
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EntityControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * @throws Exception
     * @see EntityController#entities()
     */
    @Test
    void entities() throws Exception {
        mvc.perform(get("/api/entities"))
                .andExpect(status().isOk())
                .andDo(print());
    }


}