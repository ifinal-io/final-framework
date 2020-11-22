package org.finalframework.devops.java;

import org.benf.cfr.reader.Main;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/21 23:26:47
 * @since 1.0
 */
class DecompilerTest {

    @Test
    void decompilerClass() {

        Main.main(new String[]{"--help"});

        System.out.println(Decompiler.decompile(ApplicationContext.class, null));
    }

}