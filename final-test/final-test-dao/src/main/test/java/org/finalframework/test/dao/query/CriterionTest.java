package org.finalframework.test.dao.query;


import org.finalframework.data.query.criterion.CriterionValue;
import org.finalframework.data.query.operation.DateOperation;
import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.StringOperation;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 13:53:01
 * @since 1.0
 */
public class CriterionTest {
    @Test
    public void testEqual() {
        System.out.println(QPerson.name.eq(CriterionValue.from("haha").apply(StringOperation.concat("sa", "sb"))));
        System.out.println(QPerson.created.apply(DateOperation.date()).eq(CriterionValue.from("haha").apply(StringOperation.concat("sa", "sb"))));

        System.out.println(QPerson.stringList.notJsonContains(
                CriterionValue.from("[a,b]").apply(JsonOperation.array()),
                "$"));
    }
}

