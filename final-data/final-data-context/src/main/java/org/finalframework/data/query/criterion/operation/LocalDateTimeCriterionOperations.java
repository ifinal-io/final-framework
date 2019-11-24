package org.finalframework.data.query.criterion.operation;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-23 15:40:06
 * @since 1.0
 */
public class LocalDateTimeCriterionOperations extends AbsDateCriterionOperations<LocalDateTime> {

    public static final LocalDateTimeCriterionOperations INSTANCE = new LocalDateTimeCriterionOperations();

    private LocalDateTimeCriterionOperations() {
        super(LocalDateTime.class);
    }

    @Override
    protected String date(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    @Override
    protected String dateTime(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
    }
}

