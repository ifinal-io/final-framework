package org.ifinal.finalframework.devops.java;

import org.ifinal.finalframework.devops.java.compiler.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class RedefinerTest {

    @Test
    void redefine() throws Exception {
        Main main = new Main();
        System.out.println(main.hello());

        Redefiner.redefine(Main.class, "package org.ifinal.finalframework.devops.java.compiler;\n" +
                "\n" +
                "/**\n" +
                " * @author likly\n" +
                " * @version 1.0.0\n" +
                " *\n" +
                " * @since 1.0.0\n" +
                " */\n" +
                "public class Main {\n" +
                "\n" +
                "    public String hello() {\n" +
                "        return \"hello world\";\n" +
                "    }\n" +
                "}\n");


        Class<?> clazz = Class.forName("org.ifinal.finalframework.devops.java.compiler.Main");

        System.out.println(Main.class.equals(clazz));

        Method hello = ReflectionUtils.findMethod(clazz, "hello");
        Objects.requireNonNull(hello);
        Assertions.assertEquals("hello world", ReflectionUtils.invokeMethod(hello, clazz.newInstance()));

    }

}