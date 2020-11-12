package org.finalframework.web.api.controller;

import org.finalframework.data.services.EnumServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/12 11:06:48
 * @since 1.0
 */
@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping
    public List<String> enums() {
        return EnumServices.enums();
    }

    @GetMapping("/descriptions")
    public Map<String, String> descriptions() throws Exception {
        return EnumServices.descriptions();
    }
}
