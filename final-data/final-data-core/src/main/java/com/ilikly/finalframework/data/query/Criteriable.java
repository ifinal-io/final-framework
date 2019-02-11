package com.ilikly.finalframework.data.query;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 16:47
 * @since 1.0
 */
public interface Criteriable<T> {

    //    Comparable operation

    T eq(@NotNull Object value);

    T neq(@NotNull Object value);

    T gt(@NotNull Comparable value);

    T gte(@NotNull Comparable value);

    T lt(@NotNull Comparable value);

    T lte(@NotNull Comparable value);

    //    in operation

    T in(@NotEmpty Object... values);

    T in(@NotEmpty Collection<Object> values);

    T nin(@NotEmpty Object... values);

    T nin(@NotEmpty Collection<Object> values);

    T isNull();

    T nonNull();

    //    like operation

    T startWith(@NotEmpty String value);

    T notStartWith(@NotEmpty String value);

    T endWith(@NotEmpty String value);

    T notEndWith(@NotEmpty String value);

    T contains(@NotEmpty String value);

    T notContains(@NotEmpty String value);

    T like(@NotBlank String value);

    T notLike(@NotBlank String value);

    // date operation

    T before(@NotNull Date date);

    T before(@NotNull long date);

    T after(@NotNull Date date);

    T after(@NotNull long date);

    T dateBefore(@NotNull Date date);

    T dateBefore(@NotNull long date);

    T dateAfter(@NotNull Date date);

    T dateAfter(@NotNull long date);

    <E extends Comparable> T between(@NotNull E min, @NotNull E max);

    <E extends Comparable> T notBetween(@NotNull E min, @NotNull E max);

    T dateBetween(@NotNull Date min, @NotNull Date max);

    T notDateBetween(@NotNull Date min, @NotNull Date max);

    T dateBetween(@NotNull long min, @NotNull long max);

    T notDateBetween(@NotNull long min, @NotNull long max);

}
