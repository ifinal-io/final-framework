package org.ifinal.finalframework.mybatis.dao.mapper;

import javax.annotation.Resource;
import org.ifinal.finalframework.mybatis.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * PersonMapperTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootTest
class PersonMapperTest {

    @Resource
    private PersonMapper personMapper;

    @Test
    void test() {

        Person creator = new Person();
        creator.setName("creator");
        creator.setId(2L);

        Person person = new Person();
        person.setName("person");
        person.setAge(11);
        person.setCreator(creator);

        int insert = personMapper.insert(person);

        Assertions.assertEquals(1, insert);

        Person one = personMapper.selectOne(person.getId());
        Assertions.assertEquals(creator.getId(), one.getCreator().getId());

    }

}
