package cn.com.likly.finalframework.data.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 16:47
 * @since 1.0
 */
public interface CriteriaFunction<T> {

    T is(Object value);

    T ne(Object value);

    T gt(@NotNull Object value);

    T gte(@NotNull Object value);

    T lt(@NotNull Object value);

    T lte(@NotNull Object value);

    T in(@NotEmpty Object... values);

    T in(@NotEmpty Collection<?> values);

    T nin(@NotEmpty Object... values);

    T nin(@NotEmpty Collection<?> values);

    T like(@NotBlank String value);

    T nlike(@NotBlank String value);

    <E extends Comparable> T between(@NotNull E min, @NotNull E max);

    <E extends Comparable> T notBetween(@NotNull E min, @NotNull E max);

}
