package org.finalframework.spring.web.converter;

import org.finalframework.core.Assert;
import org.finalframework.core.converter.Converter;
import org.finalframework.core.filter.Filter;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.util.Enums;
import org.finalframework.data.util.Messages;
import org.finalframework.spring.web.response.advice.EnumsResponseBodyAdvice;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 将实现了{@link IEnum}接口的枚举对象集转换成{@link Map<String,Object>}对象集。
 * <p>
 * 在调用{@link #convert(Object)}方法之前，一定要先调用 {@link #matches(Object)} 方法判断是否匹配。
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-27 10:19:24
 * @see EnumsResponseBodyAdvice
 * @since 1.0
 */
public class Enums2EnumBeansConverter implements Converter<Object, List<Map<String, Object>>>, Filter<Object> {

    @Override
    public boolean matches(Object enums) {
        if (Assert.isNull(enums)) return false;

        if (enums.getClass().isArray()) {
            if (Assert.isEmpty(enums)) return false;
            return ((Object[]) enums)[0] instanceof IEnum;
        }

        if (enums instanceof List) {
            if (Assert.isEmpty(enums)) return false;
            return ((List<?>) enums).get(0) instanceof IEnum;
        }

        if (enums instanceof Set) {
            if (Assert.isEmpty(enums)) return false;
            return ((Set<?>) enums).toArray()[0] instanceof IEnum;
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> convert(Object body) {

        if (body instanceof Collection) {
            Collection<IEnum<?>> enums = (Collection<IEnum<?>>) body;
            return enums.stream().map(this::buildEnumBean).collect(Collectors.toList());
        } else if (body.getClass().isArray()) {
            IEnum<?>[] enums = (IEnum<?>[]) body;
            return Arrays.stream(enums).map(this::buildEnumBean).collect(Collectors.toList());
        }

        throw new IllegalArgumentException("不支持的数据类型：" + body.getClass());
    }

    private Map<String, Object> buildEnumBean(IEnum<?> ienum) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", ienum.getCode());
        if (ienum instanceof Enum) {
            result.put("desc", Messages.getMessage(Enums.getEnumI18NCode((Enum<?>) ienum), ienum.getDesc()));
            result.put("name", ((Enum<?>) ienum).name());
        } else {
            result.put("desc", ienum.getDesc());
        }
        return result;
    }

}
