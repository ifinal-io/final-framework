

package org.finalframework.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.finalframework.annotation.IView;
import org.finalframework.annotation.data.AbsEntity;
import org.finalframework.annotation.result.Result;
import org.finalframework.context.user.UserContextHolder;
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
    public void testResultJson() throws JsonProcessingException {
        Result<Object> result = new Result<>();
        result.setLocale(LocaleContextHolder.getLocale());
        result.setTimeZone(LocaleContextHolder.getTimeZone());
        result.setOperator(UserContextHolder.getUser());

        AbsEntity entity = new AbsEntity();
        entity.setId(1L);
        entity.setVersion(1L);
//        entity.setLastModified(LocalDateTime.now());
        entity.setName("haha");
//        JsonViewValue jsonViewValue = new JsonViewValue(entity, IView.class);
//        result.setData(jsonViewValue);
        result.setData(entity);


//        logger.info(new ObjectMapper().writerWithView(IView.class).writeValueAsString(entity));
//        logger.info(new ObjectMapper().writerWithView(AbsEntity.class).writeValueAsString(entity));
//        logger.info(new ObjectMapper().writeValueAsString(entity));
        logger.info(Json.toJson(result));
        logger.info(Json.toJson(result, IView.class));
        logger.info(Json.toJson(result, AbsEntity.class));
    }

}
