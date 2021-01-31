package org.ifinal.finalframework.example.web.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.annotation.web.bind.RequestJsonParam;
import org.ifinal.finalframework.example.entity.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/params")
public class ListParamApiController {

    @GetMapping("/list")
    public List<String> list(final @RequestParam("args") List<String> args) {
        logger.info("{}", args);
        return args;
    }

    @GetMapping("/json")
    public Person json(final @RequestJsonParam Person person) {
        return person;
    }

}
