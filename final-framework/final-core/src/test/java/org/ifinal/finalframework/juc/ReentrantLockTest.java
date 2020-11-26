package org.ifinal.finalframework.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ReentrantLockTest {

    @Test
    void testReentrantLock() {
        ReentrantLock lock = new ReentrantLock();

        try {
            lock.lock();
        } finally {
            lock.unlock();
        }


    }
}
