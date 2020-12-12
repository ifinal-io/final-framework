package org.ifinal.finalframework.dashboard.devops.controller.api;

import org.ifinal.finalframework.dashboard.devops.model.JadModel;
import org.ifinal.finalframework.devops.java.Decompiler;
import org.ifinal.finalframework.devops.java.Redefiner;
import org.ifinal.finalframework.devops.java.compiler.Compiler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/devops/hotswap")
public class HotswapApiController {


    @GetMapping("/jad")
    public JadModel jad(final Class<?> clazz) {

        JadModel jadModel = new JadModel();
        jadModel.setSource(Decompiler.decompile(clazz, null));
        return jadModel;
    }

    @PostMapping("/compile")
    public void compile(final String clazz, final String source) {

        Compiler compiler = new Compiler(getClass().getClassLoader());
        compile(clazz, source);
        compiler.compile();
    }

    @PostMapping("/redefine")
    public void redefine(final Class<?> clazz, final String source) {

        Redefiner.redefine(clazz, source);
    }


}
