package org.finalframework.data.query.criterion;

import org.finalframework.data.entity.enums.IEnum;
import org.finalframework.data.query.criterion.function.operation.*;
import org.finalframework.util.LinkedMultiKeyMap;
import org.finalframework.util.MultiKeyMap;

import java.util.Arrays;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 15:05:19
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class FunctionOperationRegistry {

    private static final FunctionOperationRegistry INSTANCE = new FunctionOperationRegistry();

    private final MultiKeyMap<String, Class<?>, FunctionCriterionOperation<?>> operations = new LinkedMultiKeyMap<>();

    {
        registerCriterionOperation(new DateFunctionCriterionOperation());

        registerCriterionOperation(new NumberAndFunctionCriterionOperation<>());
        registerCriterionOperation(new NumberOrFunctionCriterionOperation<>());
        registerCriterionOperation(new NumberXorFunctionCriterionOperation<>());
        registerCriterionOperation(new NumberNotFunctionCriterionOperation<>());

        registerCriterionOperation(new IEnumAndFunctionCriterionOperation<>());
        registerCriterionOperation(new IEnumOrFunctionCriterionOperation<>());
        registerCriterionOperation(new IEnumXorFunctionCriterionOperation<>());
        registerCriterionOperation(new IEnumNotFunctionCriterionOperation<>());

        registerCriterionOperation(new MinFunctionCriterionOperation());
        registerCriterionOperation(new MaxFunctionCriterionOperation());
        registerCriterionOperation(new SumFunctionCriterionOperation());
        registerCriterionOperation(new AvgFunctionCriterionOperation());

        registerCriterionOperation(new JsonExtractFunctionCriterionOperation<>());
    }

    private FunctionOperationRegistry() {
    }

    public static FunctionOperationRegistry getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        new FunctionOperationRegistry();
    }

    public <T> void registerCriterionOperation(String operator, Class<T> type, FunctionCriterionOperation operation) {
        operations.add(operator, type, operation);
    }

    public <T> void registerCriterionOperation(FunctionCriterionOperation criterionOperation) {
        FunctionOperation functionOperation = criterionOperation.getClass().getAnnotation(FunctionOperation.class);
        if (functionOperation == null) {
            throw new IllegalArgumentException();
        }

        String operator = functionOperation.value();
        Class<?>[] types = functionOperation.types();

        Arrays.stream(types).forEach(type -> registerCriterionOperation(operator, type, criterionOperation));


    }

    @SuppressWarnings("unchecked")
    public <T> FunctionCriterionOperation getCriterionOperation(String operator, Class<T> type) {
        Class<?> key = type;
        if (type.isEnum()) {
            if (IEnum.class.isAssignableFrom(type)) {
                key = IEnum.class;
            }
        }

        FunctionCriterionOperation<?> functionCriterionOperation = operations.get(operator, key);

        if (functionCriterionOperation == null) {
            functionCriterionOperation = operations.get(operator, Object.class);
        }


        if (functionCriterionOperation == null) {
            throw new IllegalArgumentException(String.format("not found criterion operator of operator = %s and type = %s", operator, type.getCanonicalName()));
        }
        return functionCriterionOperation;
    }


}
