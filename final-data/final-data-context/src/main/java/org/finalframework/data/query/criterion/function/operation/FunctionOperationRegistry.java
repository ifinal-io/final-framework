package org.finalframework.data.query.criterion.function.operation;

import java.util.Arrays;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.finalframework.data.query.criterion.function.SupportFunctions;
import org.finalframework.data.query.criterion.function.expression.AvgFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.ConcatFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.DateFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.IEnumAndFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.IEnumNotFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.IEnumOrFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.IEnumXorFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.JsonContainsFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.JsonExtractFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.JsonUnquoteFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.MaxFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.MinFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.NumberAndFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.NumberNotFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.NumberOrFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.NumberXorFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.SumFunctionOperationExpression;
import org.finalframework.util.LinkedMultiKeyMap;
import org.finalframework.util.MultiKeyMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 15:05:19
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class FunctionOperationRegistry {

    private static final FunctionOperationRegistry INSTANCE = new FunctionOperationRegistry();

    private final MultiKeyMap<FunctionOperator, Class<?>, FunctionOperationExpression<? extends FunctionOperation>> operations = new LinkedMultiKeyMap<>();

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
        registerCriterionOperation(new JsonContainsFunctionOperationExpression<>());
        registerCriterionOperation(new JsonUnquoteFunctionOperationExpression<>());
    }

    private FunctionOperationRegistry() {
    }

    public static FunctionOperationRegistry getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        new FunctionOperationRegistry();
    }

    public <T> void registerCriterionOperation(FunctionOperator operator, Class<T> type,
        FunctionOperationExpression<? extends FunctionOperation> operation) {
        operations.add(operator, type, operation);
    }

    public <T> void registerCriterionOperation(FunctionOperationExpression<? extends FunctionOperation> criterionOperation) {
        SupportFunctions supportFunctions = criterionOperation.getClass().getAnnotation(SupportFunctions.class);
        if (supportFunctions == null) {
            throw new IllegalArgumentException();
        }

        FunctionOperator operator = supportFunctions.value();
        Class<?>[] types = supportFunctions.types();

        Arrays.stream(types).forEach(type -> registerCriterionOperation(operator, type, criterionOperation));


    }

    @SuppressWarnings("unchecked")
    public <T> FunctionOperationExpression<? extends FunctionOperation> getCriterionOperation(FunctionOperator operator,
        Class<T> type) {
        Class<?> key = type;
        if (type.isEnum()) {
            if (IEnum.class.isAssignableFrom(type)) {
                key = IEnum.class;
            }
        }

        FunctionOperationExpression<? extends FunctionOperation> functionOperationExpression = operations
            .get(operator, key);

        if (functionOperationExpression == null) {
            functionOperationExpression = operations.get(operator, Object.class);
        }


        if (functionOperationExpression == null) {
            throw new IllegalArgumentException(String.format("not found criterion operator of operator = %s and type = %s", operator, type.getCanonicalName()));
        }
        return functionOperationExpression;
    }


}
