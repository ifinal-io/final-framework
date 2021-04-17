package org.ifinal.finalframework.context.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.ifinal.finalframework.core.IUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * UserContextHolderTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class UserContextHolderTest {

    @Data
    @AllArgsConstructor
    static class User implements IUser<Long> {

        private Long id;

        private String name;

    }

    @Test
    void test() throws InterruptedException {

        User user = new User(1L, "xiaoMing");

        UserContextHolder.setUser(user, false);

        new Thread(() -> assertNull(UserContextHolder.getUser())).start();


        UserContextHolder.reset();
        UserContextHolder.setUser(user, true);

        new Thread(() -> assertNotNull(UserContextHolder.getUser())).start();

        Thread.sleep(1000);

    }

}
