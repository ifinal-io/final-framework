package org.finalframework.web;

import org.finalframework.annotation.data.YN;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/9 16:25:34
 * @since 1.0
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(String word) {
        return "hello " + word + "!";
    }

    @RequestMapping("/yn")
    public Class<YN> yu() {
        return YN.class;
    }


}
