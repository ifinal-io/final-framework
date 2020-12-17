package org.ifinal.finalframework.mybatis.dao.mapper;

import javax.annotation.Resource;
import org.ifinal.finalframework.mybatis.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * UserMapperTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
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

}
