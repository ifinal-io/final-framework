package cn.com.likly.finalframework.api.controller;

import cn.com.likly.finalframework.cache.annotation.CacheDel;
import cn.com.likly.finalframework.cache.annotation.CacheSet;
import cn.com.likly.finalframework.data.dao.mapper.PersonMapper;
import cn.com.likly.finalframework.data.domain.Criteria;
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
    public List<Person> list(Person person) {
        final Query query = new Query();
        query.where(
                Criteria.where(Person.name).eq(person.getName())
                        .and(Person.age).gt(person.getAge())
        );
        query.sort(Person.id.desc());
        query.page(1).size(20);
        return personMapper.select(query);
    }

    @GetMapping("/{id}")
    @CacheSet(key = "#id", condition = "#id == 2", ttl = 1, timeunit = TimeUnit.MINUTES)
    public Person get(@PathVariable Long id) {
        return personMapper.selectOne(id);
    }

    @DeleteMapping("/{id}")
    @CacheDel(key = "#id", condition = "#result")
    public boolean delete(@PathVariable Long id) {
        return personMapper.delete(id) == 1;
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
