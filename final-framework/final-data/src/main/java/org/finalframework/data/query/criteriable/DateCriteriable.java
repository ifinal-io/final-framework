package org.finalframework.data.query.criteriable;

import org.springframework.lang.NonNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-24 15:30:04
 * @since 1.0
 */
public interface DateCriteriable<R> extends FunctionCriteriable<String, R> {

    default R eq(@NonNull Date date) {
        return eq(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    default R eq(long date) {
        return eq(new Date(date));
    }

    default R eq(@NonNull LocalDateTime date) {
        return eq(date.toLocalDate());
    }

    default R eq(@NonNull LocalDate date) {
        return eq(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }


    default R neq(@NonNull Date date) {
        return neq(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    default R neq(long date) {
        return neq(new Date(date));
    }

    default R neq(@NonNull LocalDateTime date) {
        return neq(date.toLocalDate());
    }

    default R neq(@NonNull LocalDate date) {
        return neq(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }


    default R gt(@NonNull Date date) {
        return gt(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    default R gt(long date) {
        return gt(new Date(date));
    }

    default R gt(@NonNull LocalDateTime date) {
        return gt(date.toLocalDate());
    }

    default R gt(@NonNull LocalDate date) {
        return gt(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }


    default R gte(@NonNull Date date) {
        return gte(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    default R gte(long date) {
        return gte(new Date(date));
    }

    default R gte(@NonNull LocalDateTime date) {
        return gte(date.toLocalDate());
    }

    default R gte(@NonNull LocalDate date) {
        return gte(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    default R lt(@NonNull Date date) {
        return lt(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    default R lt(long date) {
        return lt(new Date(date));
    }

    default R lt(@NonNull LocalDateTime date) {
        return lt(date.toLocalDate());
    }

    default R lt(@NonNull LocalDate date) {
        return lt(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }


    default R lte(@NonNull Date date) {
        return lte(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    default R lte(long date) {
        return lte(new Date(date));
    }

    default R lte(@NonNull LocalDateTime date) {
        return lte(date.toLocalDate());
    }

    default R lte(@NonNull LocalDate date) {
        return lte(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    default R after(@NonNull Date date) {
        return after(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    default R after(long date) {
        return after(new Date(date));
    }

    default R after(@NonNull LocalDateTime date) {
        return after(date.toLocalDate());
    }

    default R after(@NonNull LocalDate date) {
        return after(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }


    default R before(@NonNull Date date) {
        return before(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    default R before(long date) {
        return before(new Date(date));
    }

    default R before(@NonNull LocalDateTime date) {
        return before(date.toLocalDate());
    }

    default R before(@NonNull LocalDate date) {
        return before(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    default R between(@NonNull Date min, @NonNull Date max) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return between(format.format(min), format.format(max));
    }

    default R between(long min, long max) {
        return between(new Date(min), new Date(max));
    }

    default R between(@NonNull LocalDateTime min, @NonNull LocalDateTime max) {
        return between(min.toLocalDate(), max.toLocalDate());
    }

    default R between(@NonNull LocalDate min, @NonNull LocalDate max) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return between(min.format(formatter), max.format(formatter));
    }

    default R notBetween(@NonNull Date min, @NonNull Date max) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return notBetween(format.format(min), format.format(max));
    }

    default R notBetween(long min, long max) {
        return notBetween(new Date(min), new Date(max));
    }

    default R notBetween(@NonNull LocalDateTime min, @NonNull LocalDateTime max) {
        return notBetween(min.toLocalDate(), max.toLocalDate());
    }

    default R notBetween(@NonNull LocalDate min, @NonNull LocalDate max) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return notBetween(min.format(formatter), max.format(formatter));
    }
}
