package org.ifinal.finalframework.example.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
public class HelloHotswapApiController {

//    @Auth
    @GetMapping("/api/hotswap/hello")
    @PreAuthorize("hasRole('admin')")
    public String hello() {
        return "hello hotswap!";
    }

}
