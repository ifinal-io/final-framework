package cn.com.likly.finalframework.api.controller;

import cn.com.likly.finalframework.dao.mapper.PersonMapper;
import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.entity.Person;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
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
        final Query<PropertyHolder> query = new Query<>();
        query.page(1).size(1);
//        query.where(
//                Criteria.where(PersonEntityHolder.id).is(id)
//        );
        return personMapper.select(
                query
        );
    }

    @GetMapping("/count")
    public long count() {
        return personMapper.selectCount();
    }

    @RequestMapping("/index")
    public String index() {
        return "{}";
    }
}
