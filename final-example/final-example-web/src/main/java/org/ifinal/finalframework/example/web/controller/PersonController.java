package org.ifinal.finalframework.example.web.controller;

import org.ifinal.finalframework.annotation.monitor.ActionMonitor;
import org.ifinal.finalframework.example.entity.Person;
import org.ifinal.finalframework.example.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Resource
    private PersonService personService;

    @GetMapping
    @ActionMonitor(name = {"查询Person", "${#result[0].id}"}, target = "${#result[0].id}")
    public List<Person> query() {
        return personService.select();
    }

}
