package com.didi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author likly
 * @version 1.0
 * @date 2020/9/25 20:34:21
 * @since 1.0
 */
class BinSearchTest {

    BinSearch search = new BinSearch();

    @Test
    void testBinSearch() {
        int size = 1;
        int[] nums = new int[size];
        // nums=[1,2,3,4,5]
        for (int i = 1; i <= size; i++) {
            nums[i - 1] = i;
        }

        assertEquals(0, search.search(nums, 1));
//        assertEquals(-1, search.search(nums, 0));
//        assertEquals(3, search.search(nums, 4));
    }

}