package org.ifinal.finalframework.devops.java;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public final class Instrumentations {

    public static Instrumentation get() {
        return ByteBuddyAgent.install();
    }

    public static void retransformClasses(Instrumentation inst, ClassFileTransformer transformer,
                                          Set<Class<?>> classes) {


        try {
            inst.addTransformer(transformer, true);

            for (Class<?> clazz : classes) {
                if (isLambdaClass(clazz)) {
                    logger.info(
                            "ignore lambda class: {}, because jdk do not support retransform lambda class: https://github.com/alibaba/arthas/issues/1512.",
                            clazz.getName());
                    continue;
                }
                try {
                    inst.retransformClasses(clazz);
                } catch (Throwable e) {
                    String errorMsg = "retransformClasses class error, name: " + clazz.getName();
                    logger.error(errorMsg, e);
                }
            }
        } finally {
            inst.removeTransformer(transformer);
        }
    }

    public static boolean isLambdaClass(Class<?> clazz) {
        return clazz.getName().contains("$$Lambda$");
    }

}
