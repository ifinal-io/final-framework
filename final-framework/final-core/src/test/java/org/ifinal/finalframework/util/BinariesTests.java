package org.ifinal.finalframework.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * BinariesTests.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class BinariesTests {

    @Test
    void flat() {
        assertTrue(Binaries.flat(7).containsAll(Arrays.asList(1,2,4)));
        assertTrue(Binaries.flat(4).contains(4));
    }

    @Test
    void merge() {
        assertEquals(7,Binaries.merge(Arrays.asList(1,2,4)));
        assertEquals(7,Binaries.merge(Arrays.asList(1,2,4,5)));
        assertEquals(7,Binaries.merge(Arrays.asList(1,3,5)));
    }

}
