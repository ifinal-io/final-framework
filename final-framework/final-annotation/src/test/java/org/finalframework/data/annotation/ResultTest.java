package org.finalframework.data.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.finalframework.annotation.result.Result;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0
 * @date 2020/9/16 17:44:53
 * @since 1.0
 */
class ResultTest {

    @Test
    void jsonTest() throws JsonProcessingException {
        Result<?> result = new Result<>();
        System.out.println(new ObjectMapper().writeValueAsString(result));

    }

}
