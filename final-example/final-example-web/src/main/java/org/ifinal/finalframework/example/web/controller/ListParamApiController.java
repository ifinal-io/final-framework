package org.ifinal.finalframework.example.web.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/list")
public class ListParamApiController {

    @GetMapping
    public List<String> list(final @RequestParam("args") List<String> args) {

        return args;
    }

}
