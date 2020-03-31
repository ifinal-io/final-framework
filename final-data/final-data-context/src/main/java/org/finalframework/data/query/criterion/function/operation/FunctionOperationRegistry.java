package org.finalframework.data.query.criterion.function.operation;

import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.query.criterion.function.SupportTypes;
import org.finalframework.data.query.criterion.function.expression.*;
import org.finalframework.data.query.operation.Operation;
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

    /**
     * 函数运算表达式
     */
    private final MultiKeyMap<Operation, Class<?>, FunctionOperationExpression<? extends FunctionOperation>> operationExpressions = new LinkedMultiKeyMap<>();

    {
        registerCriterionOperation(new DateFunctionOperationExpression());

        registerCriterionOperation(new NumberAndFunctionOperationExpression<>());
        registerCriterionOperation(new NumberOrFunctionOperationExpression<>());
        registerCriterionOperation(new NumberXorFunctionOperationExpression<>());
        registerCriterionOperation(new NumberNotFunctionOperationExpression<>());

        registerCriterionOperation(new IEnumAndFunctionOperationExpression<>());
        registerCriterionOperation(new IEnumOrFunctionOperationExpression<>());
        registerCriterionOperation(new IEnumXorFunctionOperationExpression<>());
        registerCriterionOperation(new IEnumNotFunctionOperationExpression<>());

        registerCriterionOperation(new MinFunctionOperationExpression());
        registerCriterionOperation(new MaxFunctionOperationExpression());
        registerCriterionOperation(new SumFunctionOperationExpression());
        registerCriterionOperation(new AvgFunctionOperationExpression());

        registerCriterionOperation(new ConcatFunctionOperationExpression<>());

        registerCriterionOperation(new JsonExtractFunctionOperationExpression<>());
        registerCriterionOperation(new JsonLengthFunctionOperationExpression<>());
        registerCriterionOperation(new JsonKeysFunctionOperationExpression<>());
        registerCriterionOperation(new JsonDepthFunctionOperationExpression<>());
        registerCriterionOperation(new JsonUnquoteFunctionOperationExpression<>());
        registerCriterionOperation(new JsonContainsFunctionOperationExpression<>());
    }

    private FunctionOperationRegistry() {
    }

    public static FunctionOperationRegistry getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        new FunctionOperationRegistry();
    }

    public <T> void registerCriterionOperation(Class<T> type,
                                               FunctionOperationExpression<? extends FunctionOperation> expression) {
        operationExpressions.add(expression.operation(), type, expression);
    }

    public <T> void registerCriterionOperation(FunctionOperationExpression<? extends FunctionOperation> criterionOperation) {
        SupportTypes supportTypes = criterionOperation.getClass().getAnnotation(SupportTypes.class);
        if (supportTypes == null) {
            throw new IllegalArgumentException();
        }

        Class<?>[] types = supportTypes.types();

        Arrays.stream(types).forEach(type -> registerCriterionOperation(type, criterionOperation));


    }

    @SuppressWarnings("unchecked")
    public <T> FunctionOperationExpression<? extends FunctionOperation> getCriterionOperation(Operation operation,
                                                                                              Class<T> type) {
        Class<?> key = type;
        if (type.isEnum()) {
            if (IEnum.class.isAssignableFrom(type)) {
                key = IEnum.class;
            }
        }

        FunctionOperationExpression<? extends FunctionOperation> functionOperationExpression = operationExpressions
                .get(operation, key);

        if (functionOperationExpression == null) {
            functionOperationExpression = operationExpressions.get(operation, Object.class);
        }


        if (functionOperationExpression == null) {
            throw new IllegalArgumentException(String.format("not found criterion operation of operation = %s and type = %s", operation, type.getCanonicalName()));
        }
        return functionOperationExpression;
    }


}
