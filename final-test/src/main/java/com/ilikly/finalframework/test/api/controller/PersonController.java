package com.ilikly.finalframework.test.api.controller;

import com.ilikly.finalframework.cache.annotation.CacheSet;
import com.ilikly.finalframework.data.query.QEntity;
import com.ilikly.finalframework.data.query.Query;
import com.ilikly.finalframework.data.query.Update;
import com.ilikly.finalframework.spring.aop.monitor.MethodMonitor;
import com.ilikly.finalframework.test.dao.mapper.PersonMapper;
import com.ilikly.finalframework.test.entity.Person;
import com.ilikly.finalframework.test.entity.QPerson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:35
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {
    @Resource
    private PersonMapper personMapper;

    public static void main(String[] args) {
        QEntity<Long, Person> entity = QEntity.from(Person.class);
        System.out.println(entity);
        QEntity<Long, Person> person = QPerson.Person;
        System.out.println(person);
    }

    @PostMapping
    public Person insert(@RequestBody Person person) {
        personMapper.insert(person);
        return person;
    }

    @PostConstruct
    public void init() {
        Query query = new Query().where(QPerson.id.gt(3));
        System.out.println(personMapper.selectCount(query));
    }

    @GetMapping("/count")
    public int count() {
        return personMapper.select(new Query()).size();
    }

    @PostMapping("/{id}")
    @MethodMonitor
    public int update(@PathVariable("id") Long id, @RequestBody Person person) {
        person.setId(id);
//        return personMapper.update(person);
        Update update = Update.update().set(QPerson.name, person.getName())
                .set(QPerson.creator, person.getCreator().getId());
        return personMapper.update(update, id);
    }

    @GetMapping("/{id}")
    @CacheSet(keyPattern = "person:%s:", keys = "#id", ttl = 1, timeunit = TimeUnit.MINUTES)
    public Object get(@PathVariable("id") Long id) {
        Query query = new Query().where(
                QPerson.id.eq(id).or(
                        QPerson.age.between(25, 30)
                                .and(QPerson.name.contains("test"))
                ));
        return personMapper.select(query);
    }

}
