package org.finalframework.dashboard.devops.controller.api;

import org.finalframework.dashboard.devops.model.JadModel;
import org.finalframework.devops.java.Decompiler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/22 08:25:43
 * @since 1.0
 */
@RestController
@RequestMapping("/api/devops/jad")
public class JadApiController {

    @GetMapping
    public JadModel jad(Class<?> clazz) {
        JadModel jadModel = new JadModel();
        jadModel.setSource(Decompiler.decompile(clazz, null));
        return jadModel;
    }
}
