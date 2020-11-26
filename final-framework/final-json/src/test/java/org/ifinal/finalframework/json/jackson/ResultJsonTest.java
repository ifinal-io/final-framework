package org.ifinal.finalframework.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ifinal.finalframework.annotation.IView;
import org.ifinal.finalframework.annotation.data.AbsEntity;
import org.ifinal.finalframework.annotation.result.Result;
import org.ifinal.finalframework.context.user.UserContextHolder;
import org.ifinal.finalframework.json.Json;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ResultJsonTest {

    private static final Logger logger = LoggerFactory.getLogger(ResultJsonTest.class);


    @Test
    public void testResultJson() throws JsonProcessingException {
        Result<?> result = new Result<>();
        result.setLocale(LocaleContextHolder.getLocale());
        result.setTimeZone(LocaleContextHolder.getTimeZone());
        result.setOperator(UserContextHolder.getUser());

        AbsEntity entity = new AbsEntity();
        entity.setId(1L);
        entity.setVersion(1L);


//        logger.info(new ObjectMapper().writerWithView(IView.class).writeValueAsString(entity));
//        logger.info(new ObjectMapper().writerWithView(AbsEntity.class).writeValueAsString(entity));
//        logger.info(new ObjectMapper().writeValueAsString(entity));
        logger.info(Json.toJson(result));
        logger.info(Json.toJson(result, IView.class));
        logger.info(Json.toJson(result, AbsEntity.class));
    }

}
