package com.ilikly.finalframework.core;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-26 15:57:32
 * @since 1.0
 */
public class AssertTest {


    @Test
    public void nonEmpty() {
        assertTrue("ys in notEmpty ", Assert.nonEmpty("ys"));
    }
}
