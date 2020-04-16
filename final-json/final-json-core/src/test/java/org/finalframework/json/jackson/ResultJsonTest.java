package org.finalframework.json.jackson;

import org.finalframework.data.result.Result;
import org.finalframework.data.user.UserContextHolder;
import org.finalframework.json.Json;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-16 23:05:23
 * @since 1.0
 */
public class ResultJsonTest {

    private static final Logger logger = LoggerFactory.getLogger(ResultJsonTest.class);


    @Test
    public void testResultJson() {
        Result<Object> result = new Result<>();
        result.setLocale(LocaleContextHolder.getLocale());
        result.setTimeZone(LocaleContextHolder.getTimeZone());
        result.setOperator(UserContextHolder.getUser());

        logger.info(Json.toJson(result));
    }

}
