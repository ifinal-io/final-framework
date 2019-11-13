package org.finalframework.data.query;

import org.finalframework.data.entity.enums.IEnum;
import org.finalframework.data.query.function.FunctionOperations;
import org.finalframework.data.query.function.operation.BaseFunctionOperations;
import org.finalframework.data.query.function.operation.DateFunctionOperation;
import org.finalframework.data.query.function.operation.EnumBitFunctionOperations;
import org.finalframework.data.query.function.operation.NumberFunctionOperations;

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
public class FunctionOperationRegistry {

    private static final FunctionOperationRegistry INSTANCE = new FunctionOperationRegistry();

    private final Map<Class, FunctionOperations> cache = new ConcurrentHashMap<>(32);

    {

        registerCriterionOperation(Date.class, new DateFunctionOperation());
        registerCriterionOperations(Byte.class, new NumberFunctionOperations<>(Byte.class));
        registerCriterionOperations(byte.class, new NumberFunctionOperations<>(byte.class));
        registerCriterionOperations(Short.class, new NumberFunctionOperations<>(Short.class));
        registerCriterionOperations(short.class, new NumberFunctionOperations<>(short.class));
        registerCriterionOperations(Integer.class, new NumberFunctionOperations<>(Integer.class));
        registerCriterionOperations(int.class, new NumberFunctionOperations<>(int.class));
        registerCriterionOperations(Long.class, new NumberFunctionOperations<>(Long.class));
        registerCriterionOperations(long.class, new NumberFunctionOperations<>(long.class));
        registerCriterionOperations(IEnum.class, new EnumBitFunctionOperations<>(IEnum.class));

    }

    private FunctionOperationRegistry() {
    }

    public static FunctionOperationRegistry getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        new FunctionOperationRegistry();
    }

    public <T> void registerCriterionOperations(Class<T> type, FunctionOperations<T> criterionOperations) {
        cache.put(type, criterionOperations);
    }

    public <T> void registerCriterionOperation(Class<T> type, FunctionOperation criterionOperation) {

        if (!cache.containsKey(type)) {
            cache.put(type, new BaseFunctionOperations(type));
        }
        final FunctionOperations criterionOperations = cache.get(type);
        criterionOperations.register(criterionOperation);
    }


    @SuppressWarnings("unchecked")
    public <T> FunctionOperation getCriterionOperation(FunctionOperator operator, Class<T> type) {
        Class<?> key = type;
        if (type.isEnum()) {
            if (IEnum.class.isAssignableFrom(type)) {
                key = IEnum.class;
            }
        }

        final FunctionOperations criterionOperations = cache.get(key);

        if (criterionOperations == null) {
            throw new UnsupportedOperationException("不支持的类型:" + type.getCanonicalName());
        }
        FunctionOperation criterionOperation = criterionOperations.get(operator);
        if (criterionOperation == null) {
            throw new IllegalArgumentException(String.format("not found criterion operator of operator = %s and type = %s", operator.name(), type.getCanonicalName()));
        }
        return criterionOperation;
    }


}
