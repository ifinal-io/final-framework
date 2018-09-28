package cn.com.likly.finalframework.api.controller;

import cn.com.likly.finalframework.dao.mapper.PersonMapper;
import cn.com.likly.finalframework.entity.Person;
import cn.com.likly.finalframework.mybatis.criteria.Criteria;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:35
 * @since 1.0
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    @Resource
    private PersonMapper personMapper;

    @PostMapping()
    public Person create(@RequestBody Person person) {
        personMapper.insert(person);
        return person;
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        return personMapper.select(Criteria.builder()
                .and("id").in(id)
                .build()
        );
    }

    @RequestMapping("/index")
    public String index() {
        return "{}";
    }
}
