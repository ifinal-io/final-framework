package com.ilikly.finalframework.test.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ilikly.finalframework.cache.annotation.CachePut;
import com.ilikly.finalframework.cache.annotation.Cacheable;
import com.ilikly.finalframework.data.query.QEntity;
import com.ilikly.finalframework.data.query.Query;
import com.ilikly.finalframework.data.query.Update;
import com.ilikly.finalframework.spring.aop.monitor.MethodMonitor;
import com.ilikly.finalframework.spring.web.resolver.annotation.RequestJsonParam;
import com.ilikly.finalframework.test.dao.mapper.PersonMapper;
import com.ilikly.finalframework.test.entity.Person;
import com.ilikly.finalframework.test.entity.QPerson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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
@Service
@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Resource
    private PersonMapper personMapper;

    public static void main(String[] args) {
        QEntity<Long, Person> entity = QEntity.from(Person.class);
        System.out.println(entity);
        QEntity<Long, Person> person = QPerson.Person;
        System.out.println(person);
    }

    @PostMapping("/param")
    public Object param(@RequestJsonParam Person param) {
        return param;
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
                .set(QPerson.creatorId, person.getCreator().getId());
        return personMapper.update(update, id);
    }

    @GetMapping("/index")
    public void index() {

    }

    @GetMapping("/{id}")
    @JsonView(Person.class)
    @Cacheable(key = {"person", "{#id}"}, ttl = 1, timeunit = TimeUnit.MINUTES)
    @CachePut(key = {"personhash", "{#id}"}, field = "name", result = "#result.name")
    public Object get(@PathVariable("id") Long id) {
        return findById(id);
    }

    @JsonView(Person.class)
    @Cacheable(key = {"'person'", "{#id}"}, ttl = 1, timeunit = TimeUnit.MINUTES)
    public Person findById(Long id) {
        return personMapper.selectOne(id);
    }
}
