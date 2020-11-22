package org.finalframework.devops.java.compiler;

import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/22 12:08:38
 * @since 1.0
 */
class CompilerTest {


    @Test
    void compiler() throws Exception {

        Compiler compiler = new Compiler(getClass().getClassLoader());

        compiler.addSource("org.finalframework.devops.java.compiler.Main", "package org.finalframework.devops.java.compiler;\n" +
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


        Map<String, Class<?>> build = compiler.build();
        Class<?> clazz = build.get("org.finalframework.devops.java.compiler.Main");


        Method hello = ReflectionUtils.findMethod(clazz, "hello");
        System.out.println(ReflectionUtils.invokeMethod(hello, clazz.newInstance()));


    }


}