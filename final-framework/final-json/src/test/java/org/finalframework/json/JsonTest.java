package org.finalframework.json;

import lombok.Data;
import org.finalframework.annotation.data.YN;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/11 09:55:42
 * @since 1.0
 */
class JsonTest {

    @Data
    static class DateBean {
        private Date date;
        private LocalDateTime localDateTime;
    }

    @Test
    void jsonDate() {
        DateBean dateBean = new DateBean();
        dateBean.setDate(new Date());
        dateBean.setLocalDateTime(LocalDateTime.now());
        System.out.println(Json.toJson(dateBean));
    }

    @Data
    static class EnumBean {
        private YN yn = YN.YES;
    }

    @Test
    void jsonEnum() {
        Assertions.assertEquals(YN.YES.getCode().toString(), Json.toJson(YN.YES));
        System.out.println(Json.toJson(new EnumBean()));
    }
}
