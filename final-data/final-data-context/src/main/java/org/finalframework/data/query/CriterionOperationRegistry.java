package org.finalframework.data.query;

import org.finalframework.core.PrimaryTypes;
import org.finalframework.data.entity.enums.IEnum;
import org.finalframework.data.query.criterion.CriterionOperations;
import org.finalframework.data.query.criterion.operation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 15:05:19
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class CriterionOperationRegistry {

    private static final CriterionOperationRegistry INSTANCE = new CriterionOperationRegistry();

    private final Map<Class, CriterionOperations> cache = new ConcurrentHashMap<>(32);

    {
        PrimaryTypes.ALL.stream().filter(it -> it != String.class).forEach(it -> registerCriterionOperations(it, new ObjectCriterionOperations<>(it)));

        registerCriterionOperations(Object.class, ObjectCriterionOperations.INSTANCE);
        registerCriterionOperations(Date.class, DateCriterionOperations.INSTANCE);
        registerCriterionOperations(LocalDateTime.class, LocalDateTimeCriterionOperations.INSTANCE);
        registerCriterionOperations(String.class, StringCriterionOperations.INSTANCE);
        registerCriterionOperations(IEnum.class, EnumCriterionOperations.INSTANCE);

    }

    private CriterionOperationRegistry() {
    }

    public static CriterionOperationRegistry getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        new CriterionOperationRegistry();
    }

    public <T> void registerCriterionOperations(Class<T> type, CriterionOperations<T> criterionOperations) {
        cache.put(type, criterionOperations);
    }

    public <T> void registerCriterionOperation(Class<T> type, CriterionOperation criterionOperation) {

        if (!cache.containsKey(type)) {
            cache.put(type, new BaseCriterionOperations(type));
        }
        final CriterionOperations criterionOperations = cache.get(type);
        criterionOperations.register(criterionOperation);
    }


    @SuppressWarnings("unchecked")
    public <T> CriterionOperation getCriterionOperation(CriterionOperator operator, Class<T> type) {

        Class<?> key = type;
        if (type.isEnum()) {
            if (IEnum.class.isAssignableFrom(type)) {
                key = IEnum.class;
            }
        }

        final CriterionOperations criterionOperations = cache.containsKey(key) ? cache.get(key) : cache.get(Object.class);

        if (criterionOperations == null) {
            throw new UnsupportedOperationException("不支持的类型:" + type.getCanonicalName());
        }
        CriterionOperation criterionOperation = criterionOperations.get(operator);
        if (criterionOperation == null) {
            throw new IllegalArgumentException(String.format("not found criterion operator of operator = %s and type = %s", operator.name(), type.getCanonicalName()));
        }
        return criterionOperation;
    }


}
