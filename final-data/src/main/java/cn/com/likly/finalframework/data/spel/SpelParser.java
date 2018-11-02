package cn.com.likly.finalframework.data.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 17:30
 * @since 1.0
 */
public class SpelParser {

    private final static ExpressionParser parser = new SpelExpressionParser();

    public static void main(String[] args) {
        System.out.println(parser.parseExpression("{1,2,3,4}").getValue().getClass().getName());
        StandardEvaluationContext context = new StandardEvaluationContext(true);
        Person person = new Person("hello", 123);
//        System.out.println(parser.parseExpression("systemProperties['JAVA_HOME']").getValue());
        System.out.println(parser.parseExpression("#root").getValue(true));
        System.out.println(parser.parseExpression("#root").getValue(person));
        System.out.println(parser.parseExpression("name + 'test'").getValue(person));
    }

    static class Person {
        private String name;
        private Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
