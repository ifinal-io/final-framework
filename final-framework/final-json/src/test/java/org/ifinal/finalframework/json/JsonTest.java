package org.ifinal.finalframework.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.annotation.core.IEnum;
import org.ifinal.finalframework.util.format.DateFormatter;
import org.ifinal.finalframework.util.format.LocalDateTimeFormatter;
import org.junit.jupiter.api.Test;

/**
 * JsonTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class JsonTest {

    @Test
    void toJson() {
        assertEquals("null", Json.toJson(null));
        assertEquals(YN.YES.getCode().toString(), Json.toJson(YN.YES));
        assertEquals(YN.YES, Json.toObject("1", YN.class));
    }

    @Test
    void toJsonOfDate() {
        DateEntity entity = new DateEntity();
        entity.setDate(new Date());
        entity.setLocalDate(LocalDate.now());
        entity.setLocalDateTime(LocalDateTime.now());
        String json = Json.toJson(entity);
        logger.info(json);
        assertEquals(6, Json.toObject(json, Map.class).size());
    }

    @Test
    void toObjectOfDate() {

        final String pattern = "yyyy-MM-dd HH:mm:ss";

        long now = System.currentTimeMillis();

        String date = DateFormatter.YYYY_MM_DD_HH_MM_SS.format(new Date(now));

        assertEquals(date, DateFormatter.YYYY_MM_DD_HH_MM_SS.format(Json.toObject(Json.toJson(now), Date.class)));
        assertEquals(date, LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS.format(Json.toObject(Json.toJson(now), LocalDateTime.class)));

    }

    @Test
    void toObjectOfNull() {
        assertNull(Json.toObject(null));
        assertNull(Json.toList(null, Object.class));
        assertNull(Json.toSet(null, Object.class));
    }

    @Test
    void toObjectOfString() {
        String json = "{\"name\":\"haha\"}";
        assertEquals(json, Json.toObject(json, String.class));
    }

    /**
     * JsonEntity.
     *
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    @Data
    static
    class DateEntity {

        private Date date;

        private LocalDate localDate;

        private LocalDateTime localDateTime;

    }

    /**
     * YN.
     *
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    @Getter
    @AllArgsConstructor
    enum YN implements IEnum<Integer> {

        YES(1, "YES"), NO(2, "NO");

        private final Integer code;

        private final String desc;

    }

}
