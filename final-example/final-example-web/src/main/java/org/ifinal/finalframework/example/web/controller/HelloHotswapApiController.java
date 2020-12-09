package org.ifinal.finalframework.example.web.controller;

import org.ifinal.finalframework.auth.annotation.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
public class HelloHotswapApiController {

    @Auth
    @GetMapping("/api/hotswap/hello")
    public String hello() {
        return "hello hotswap!";
    }

}
