package org.finalframework.web;

import org.finalframework.annotation.data.YN;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/9 16:40:55
 * @since 1.0
 */
@RestController
@RequestMapping("/enums")
public class EnumController {
    @GetMapping("/yn")
    public Class<YN> yn() {
        return YN.class;
    }
}
