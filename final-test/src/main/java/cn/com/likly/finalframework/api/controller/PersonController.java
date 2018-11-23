package cn.com.likly.finalframework.api.controller;

import cn.com.likly.finalframework.cache.annotation.CacheSet;
import cn.com.likly.finalframework.data.dao.mapper.PersonMapper;
import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.entity.Person;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.com.likly.finalframework.data.query.QPerson.Person;


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

    @GetMapping("/list")
    public List<Person> list() {
        final Query query = new Query();
        query.sort(Person.id.desc());
        return personMapper.select(query);
    }

    @GetMapping("/{id}")
    @CacheSet(key = "'person'", field = "#id", condition = "#id != 2", ttl = 20, timeunit = TimeUnit.SECONDS)
    public Person get(@PathVariable Long id) {
        return personMapper.selectOne(id);
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
