

package org.finalframework.util;

import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-26 16:39:41
 * @since 1.0
 */
class NetWorksTest {


    @Test
    void localHost() {
        System.out.println(NetWorks.localHost());
    }


    @Test
    void localPort() {
        System.out.println(NetWorks.localPort());
    }
}