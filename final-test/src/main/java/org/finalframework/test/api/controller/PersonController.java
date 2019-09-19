package org.finalframework.test.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.finalframework.cache.annotation.CacheIncrement;
import org.finalframework.cache.annotation.CacheValue;
import org.finalframework.core.Assert;
import org.finalframework.core.formatter.DateFormatter;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;
import org.finalframework.data.repository.Scanner;
import org.finalframework.json.Json;
import org.finalframework.monitor.annotation.MonitorAlert;
import org.finalframework.monitor.annotation.MonitorTrace;
import org.finalframework.monitor.annotation.OperationAction;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.finalframework.spring.aop.annotation.OperationAttribute;
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

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


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

    @PostMapping("/date")
    public String date(Date date) {
        return DateFormatter.YYYY__MM__DD_HH_MM_SS.format(date);
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
        personMapper.insert(person);
        return person;
    }

    @GetMapping("/scan")
    public void scan(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "size", required = false, defaultValue = "2") Integer size) {

        new Scanner<List<Person>>() {
            Long lastId = null;
            @Override
            public List<Person> onScan(Integer index) {
                logger.info("scan index = {}", index);
                final Query query = new Query().page(1, size);
                if (lastId != null) {
                    query.where(QPerson.id.gt(lastId));
                }
                final List<Person> list = personMapper.select(query);
                if (Assert.nonEmpty(list)) {
                    lastId = list.get(list.size() - 1).getId();
                }
                return list;
            }
        }.scan(data -> {
            logger.info(Json.toJson(data));
            return true;
        });


//        personMapper.onScan(new Query().page(page, size), new ScanListener<Person>() {
//            @Override
//            public boolean onScanning(Page<Person> list) {
//                logger.info(Json.toJson(list));
//                return true;
//            }
//        });
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

    @MonitorAlert(name = "测试Alert", key = "alert")
    @MonitorTrace
    @GetMapping("/index")
    public void index(@RequestParam("id") Long id) {

    }

    @JsonView(Person.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CacheIncrement(key = {"invoke:{#id}"}, point = CutPoint.BEFORE)
    @OperationAction(name = "查询用户", operator = "{-1}", target = "{#id}",
            type = 1,
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
