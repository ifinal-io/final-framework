package org.ifinal.finalframework.context.expression;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * SpelTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class SpelTest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Person {

        private String name;

        private Integer age;

        private Date date;

        private Person creator;

    }

    @Test
    void stand() {
        Person person = new Person("xiaoming", 12, new Date(),null);
        StandardEvaluationContext context = new StandardEvaluationContext(person);
        logger.info("name={}", Spel.getValue("name", context));
        logger.info("date={}", Spel.getValue("new java.text.SimpleDateFormat('yyyy-MM-dd').format(date)", person));

//        Spel.setValue("creator",person,new Person());
        logger.info("creator.name={}", Spel.getValue("creator.name", person));
        Spel.setValue("creator.name",person,"123321");
        logger.info("creator.name={}", Spel.getValue("creator.name", person));


        Spel.setValue("name", person, "haha");
        Assertions.assertEquals("haha", Spel.getValue("name", person));
        logger.info("name={}", Spel.getValue("name", person));



        Map<String, Object> params = new HashMap<>();
        params.put("name", "xiaoMing");
        params.put("age", 12);
        logger.info("name={}", Spel.expression("['name']").getValue(params));

        Map<String, Object> ext = new HashMap<>();
        ext.put("date", new Date());
        ext.put("name", "po");

//        params.put("ext", ext);

        context = new StandardEvaluationContext(params);
        context.addPropertyAccessor(new MapAccessor());

//        Spel.setValue("['ext']['name']",params,"456789");
        Spel.setValue("ext",context,new HashMap<>());
        Spel.setValue("ext.name",context,"aaaa");
//        Spel.setValue("ext.date",context,new Date());

        logger.info("ext.name={}", Spel.getValue("ext.name", context));
        logger.info("date={}", Spel.getValue("ext.containsKey('date') ? new java.text.SimpleDateFormat('yyyy-MM-dd').format(ext.date) : ''", context));



    }

    @Test
    void withParserContext(){
        Expression expression = Spel.expression("hello #{3+4}", ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        logger.info("hello #{3+4}={}",value);
        Assertions.assertEquals("hello 7",value);
    }

}
