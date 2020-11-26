package org.ifinal.finalframework.web;

import org.ifinal.finalframework.annotation.data.YN;
import org.ifinal.finalframework.context.exception.ServiceException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping
    public String hello(String word) {
        return "hello " + word + "!";
    }


    @RequestMapping("/ex")
    public void ex(Integer code, String message) {
        throw new ServiceException(code, message);
    }

    @RequestMapping("/yn")
    public Class<YN> yu() {
        return YN.class;
    }


}
