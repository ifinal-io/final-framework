package org.finalframework.data.converter;

import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.util.Enums;
import org.finalframework.data.util.Messages;
import org.finalframework.spring.annotation.factory.SpringComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-08 14:34:47
 * @since 1.0
 */
@EnumTarget(Enum.class)
@SpringComponent
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
    public Map<String, Object> convert(T source) {
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
