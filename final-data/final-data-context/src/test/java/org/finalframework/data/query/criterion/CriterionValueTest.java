package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.IntegerTypeHandler;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.entity.AbsEntity;
import org.finalframework.data.query.AbsQEntity;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.operator.FunctionOperator;
import org.finalframework.data.query.function.operation.SingleFunctionOperation;
import org.junit.Test;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-02 09:27:44
 * @since 1.0
 */
public class CriterionValueTest {

    @Test
    public void test() {

        AbsQEntity<Long, AbsEntity> entity = new AbsQEntity<>(AbsEntity.class);
        entity.addProperty(QProperty.builder(entity, org.finalframework.data.entity.enums.YN.class)
                .path("yn").name("yn").column("yn")
                .idProperty(false).persistentType(PersistentType.AUTO)
                .insertable(false).updatable(true).selectable(true)
                .build());


//        CriterionValue.Builder<Integer> builder = CriterionValue.builder(1);
        CriterionValue.Builder<QProperty> builder = CriterionValue.builder(entity.getProperty("yn"));
        builder.addFunction(new SingleFunctionOperation<>(FunctionOperator.AND, 2));
        builder.javaType(Integer.class)
                .typeHandler(IntegerTypeHandler.class);
        CriterionValue<?> criterionValue = builder.build();
        System.out.println(criterionValue.getSql());
        System.out.println(criterionValue.getSqlExpression("item.value"));
    }

}