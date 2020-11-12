package org.finalframework.web.api.controller;

import org.finalframework.annotation.IEnum;
import org.finalframework.io.support.ServicesLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return ServicesLoader.load(IEnum.class);
    }
}
