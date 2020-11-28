package org.ifinal.finalframework.devops.java.compiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class CompilerTest {


    @Test
    void compiler() throws Exception {

        Compiler compiler = new Compiler(getClass().getClassLoader());

        compiler.addSource("org.ifinal.finalframework.devops.java.compiler.Main", "package org.ifinal.finalframework.devops.java.compiler;\n" +
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
                "        return \"hello fefe\";\n" +
                "    }\n" +
                "}\n");


        Map<String, Class<?>> build = compiler.build();
        Class<?> clazz = build.get("org.ifinal.finalframework.devops.java.compiler.Main");


        Method hello = ReflectionUtils.findMethod(clazz, "hello");
        Objects.requireNonNull(hello);
        Assertions.assertEquals("hello fefe", ReflectionUtils.invokeMethod(hello, clazz.newInstance()));


    }


}