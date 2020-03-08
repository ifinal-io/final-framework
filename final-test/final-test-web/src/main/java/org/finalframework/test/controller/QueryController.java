package org.finalframework.test.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.test.dao.mapper.PersonMapper;
import org.finalframework.test.dao.query.PersonQuery;
import org.finalframework.test.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-23 16:22:54
 * @since 1.0
 */
@RestController
@RequestMapping("/query")
public class QueryController {
    public static final Logger logger = LoggerFactory.getLogger(QueryController.class);

    @Resource
    private PersonMapper personMapper;

    @GetMapping
    @JsonView(Person.class)
    public Object query() {
//        Query query = new Query();


//        QPerson.properties.extract("$.name").contains("中国");

//        Criterion criterion = QPerson.created.date().eq("2019-12-20 10:20:30");
//        criterion.getFunctions().add(new SimpleFunctionOperation(FunctionExpression.DATE));

//        query.where(QPerson.name.contains("111"));
        PersonQuery query = new PersonQuery();
//        query.orCriteria().addIdEqual(1L).addIdIsNull();
//        query.andCriteria().addIdGreaterThan(2L);

        query.andCriteria().addAgeEqual(20);

//        personMapper.delete(new Query().limit(1), new Listener<Void, Integer>() {
//            @Override
//            public boolean onListening(int index, Void aVoid, Integer integer) {
//                return true;
//            }
//        });


//        query.andCriteria().addIdIn(2L);
//        QPerson.properties.extract("$.name")
//        query.andCriteria().add(QPerson.properties.extract("$.name").contains("22"));
//        query.andCriteria().addCreatedDateAfter(LocalDateTime.now());
//        query.andCriteria().addCreatedLessThan(LocalDateTime.now());
//        query.andCriteria().add(QPerson.intList.extract("$[1]").eq(2));
//        query.andCriteria().addNameContains("1").addAgeGreaterThan(1);//.addLastModifiedDateBetween("2019-12-01","2019-12-12");
//        query.andCriteria().addMaxAgeGreaterThan(1);
//        query.andCriteria().addAgeMaxGreaterThan(1);
//        query.andGroupByName();
//        query.andOrderByAgeDesc();

//                .addCreatedEqual("2019-11-23")
//                .addCreatedEqual("")
//                .addIdEqual(1L)
//                .addNameContains("1");
//                .addCreatedDateEqual(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        return personMapper.select(query);
//        return null;
    }
}

