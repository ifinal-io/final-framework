package org.finalframework.devops.java;

import org.finalframework.devops.java.compiler.Main;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/22 12:38:16
 * @since 1.0
 */
class RedefinerTest {

    @Test
    void redefine() throws Exception {
        Main main = new Main();
        System.out.println(main.hello());

        Redefiner.redefine(Main.class, "package org.finalframework.devops.java.compiler;\n" +
                "\n" +
                "/**\n" +
                " * @author likly\n" +
                " * @version 1.0\n" +
                " * @date 2020/11/22 12:09:42\n" +
                " * @since 1.0\n" +
                " */\n" +
                "public class Main {\n" +
                "\n" +
                "    public String hello() {\n" +
                "        return \"hello fefe\";\n" +
                "    }\n" +
                "}\n");


        Class<?> clazz = Class.forName("org.finalframework.devops.java.compiler.Main");

        System.out.println(Main.class.equals(clazz));

        Method hello = ReflectionUtils.findMethod(clazz, "hello");
        System.out.println(ReflectionUtils.invokeMethod(hello, clazz.newInstance()));

    }

}