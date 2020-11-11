package org.finalframework.web;

import org.finalframework.annotation.data.YN;
import org.finalframework.context.exception.ServiceException;
import org.finalframework.monitor.annotation.MonitorAction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/9 16:25:34
 * @since 1.0
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping
    @MonitorAction("${'{访问Hello} ' + #word}")
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
