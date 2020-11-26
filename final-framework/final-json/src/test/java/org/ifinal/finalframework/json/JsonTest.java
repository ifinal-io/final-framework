package org.ifinal.finalframework.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.ifinal.finalframework.annotation.data.YN;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */

class JsonTest {

    @Test
    void jsonDate() throws Exception {
        DateBean dateBean = new DateBean();
        dateBean.setDate(new Date());
        dateBean.setLocalDateTime(LocalDateTime.now());
        System.out.println(new ObjectMapper().writeValueAsString(new DateBean()));
        System.out.println(Json.toJson(dateBean));
    }

    @Test
    void jsonEnum() {
        Assertions.assertEquals(YN.YES.getCode().toString(), Json.toJson(YN.YES));
        System.out.println(Json.toJson(new EnumBean()));
    }

    @Data
    static class DateBean {
        private Date date = new Date();
        private LocalDateTime localDateTime;
    }

    @Data
    static class EnumBean {
        private YN yn = YN.YES;
    }
}
