package cn.com.likly.finalframework.api.controller;

import cn.com.likly.finalframework.dao.mapper.PersonMapper;
import cn.com.likly.finalframework.data.domain.QProperty;
import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.entity.Person;
import cn.com.likly.finalframework.data.mapping.Entity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {

        Entity<Person> entity = Entity.from(Person.class);
        QProperty id = (QProperty) entity.getRequiredPersistentProperty("id");
        Query query = new Query().where(id.gt(3));

        System.out.println(personMapper.selectCount(query));
    }

    @PostMapping
    public Person insert(@RequestBody Person person) {
        personMapper.insert(person);
        return person;
    }

    @PostMapping("/{id}")
    public int update(@PathVariable("id") Long id, @RequestBody Person person) {
        person.setId(id);
        return personMapper.update(person);
    }

}
