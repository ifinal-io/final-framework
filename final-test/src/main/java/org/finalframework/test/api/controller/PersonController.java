package org.finalframework.test.api.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.finalframework.cache.annotation.CacheIncrement;
import org.finalframework.cache.annotation.CacheValue;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;
import org.finalframework.json.Json;
import org.finalframework.monitor.action.annotation.OperationAction;
import org.finalframework.monitor.action.annotation.OperationAttribute;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.finalframework.spring.web.resolver.annotation.RequestJsonParam;
import org.finalframework.test.dao.mapper.PersonMapper;
import org.finalframework.test.entity.Person;
import org.finalframework.test.entity.QPerson;
import org.finalframework.test.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


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
    @Resource
    private PersonService personService;

    public static void main(String[] args) {

    }

    @PostMapping("/param")
    public Object param(@RequestJsonParam Person param) {
        return param;
    }

    @PostMapping("/jsonObject")
    public Object jsonObject(@RequestJsonParam("json") JSONObject jsonObject) {
        return jsonObject;
    }

    @PostMapping
    public Person insert(@RequestBody Person person) {
        personMapper.insert(Person.class, person);
        return person;
    }

    @PostConstruct
    public void init() {
        Query query = new Query().where(QPerson.age.and(2).eq(2), QPerson.name.in("123", "321")).desc(QPerson.age);
        System.out.println(personMapper.selectCount(query));
    }

    @GetMapping("/count")
    public int count() {
        return personMapper.select(new Query()).size();
    }

    @PostMapping("/{id}")
    public int update(@PathVariable("id") Long id, @RequestBody Person person) {
        person.setId(id);
//        return personMapper.update(person);
        Update update = Update.update().set(QPerson.name, person.getName())
                .set(QPerson.creator, person.getCreator().getId());
        return personMapper.update(update, id);
    }

    @GetMapping("/index")
    public void index() {

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CacheIncrement(key = {"invoke:{#id}"}, point = CutPoint.BEFORE)
    @OperationAction(name = "查询用户", operator = "{-1}", target = "{#id}",
            attributes = {
                    @OperationAttribute(name = "name", value = "{#result.name}")
            })
    public Person get(@PathVariable("id") Long id, @CacheValue(key = {"invoke:{#id}"}) Long cahce) {
        logger.info(Json.toJson(cahce));
        return findById(id);
    }

    @OperationAction(name = "内部调用", target = "{#id}")
    public Person findById(Long id) {
        return personService.findById(id);
    }


}
