package org.ifinal.finalframework.context.converter;

import java.util.HashMap;
import java.util.Map;
import org.ifinal.finalframework.context.util.Messages;
import org.ifinal.finalframework.origin.IEnum;
import org.ifinal.finalframework.util.Enums;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnumTarget(Enum.class)
@Component
public class IEnumConverter<T extends Enum<T>> implements EnumConverter<T> {

    /**
     * @see Enum#ordinal()
     */
    protected static final String ENUM_ORDINAL = "ordinal";

    /**
     * @see Enum#name()
     */
    protected static final String ENUM_NAME = "name";

    /**
     * @see IEnum#getCode()
     */
    protected static final String ENUM_CODE = "code";

    /**
     * @see IEnum#getDesc()
     */
    protected static final String ENUM_DESC = "desc";

    @Override
    public Map<String, Object> convert(final T source) {

        final Map<String, Object> result = new HashMap<>();
        result.put(ENUM_NAME, source.name());
        result.put(ENUM_ORDINAL, source.ordinal());

        if (source instanceof IEnum) {
            result.put(ENUM_CODE, ((IEnum<?>) source).getCode());
            result.put(ENUM_DESC, Messages.getMessage(Enums.getEnumI18NCode(source), ((IEnum<?>) source).getDesc()));
        }
        return result;
    }

}
