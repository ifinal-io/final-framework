package cn.com.likly.finalframework.test.api.controller;

import cn.com.likly.finalframework.data.query.QEntity;
import cn.com.likly.finalframework.data.query.Query;
import cn.com.likly.finalframework.data.query.Update;
import cn.com.likly.finalframework.spring.monitor.MethodMonitor;
import cn.com.likly.finalframework.test.dao.mapper.PersonMapper;
import cn.com.likly.finalframework.test.entity.Person;
import cn.com.likly.finalframework.test.entity.QPerson;
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

    public Person get(@PathVariable("id") Long id) {
        return personMapper.selectOne(id);
    }

}
