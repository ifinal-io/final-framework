package org.ifinal.finalframework.mybatis.dao.mapper;

import javax.annotation.Resource;
import org.ifinal.finalframework.mybatis.dao.query.PersonQuery;
import org.ifinal.finalframework.mybatis.entity.User;
import org.ifinal.finalframework.query.Direction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * UserMapperTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void test() {
        User user = new User();
        user.setName("name");
        user.setAge(1);
        int save = userMapper.insert(user);
        Assertions.assertEquals(1, save);
        User db = userMapper.selectOne(user.getId());
        Assertions.assertEquals(user.getName(), db.getName());
    }

    @Test
    void orderQuery() {
        User user = new User();
        user.setName("name");
        user.setAge(1);
        userMapper.insert(user);
        user.setAge(100);
        userMapper.insert(user);
        PersonQuery query = new PersonQuery();
        query.setOrderByName(Direction.ASC);
        query.setOrderByAge(Direction.DESC.getValue());
        query.setLimit(1);
        User result = userMapper.selectOne(query);

        Assertions.assertEquals(100, result.getAge());

    }

}
