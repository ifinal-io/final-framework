package org.ifinal.finalframework.data.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ifinal.finalframework.annotation.result.Result;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ResultTest {

    @Test
    void jsonTest() throws JsonProcessingException {
        Result<?> result = new Result<>();
        System.out.println(new ObjectMapper().writeValueAsString(result));

    }

}
