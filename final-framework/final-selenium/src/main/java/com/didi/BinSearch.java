package com.didi;

/**
 * @author likly
 * @version 1.0
 * @date 2020/9/25 20:28:24
 * @since 1.0
 */
public class BinSearch {


    /**
     * 二分查找（非递归），返回目标数据在源数据中的下标
     *
     * @param nums   源数据,有序的
     * @param target 目标数据
     * @return
     */
    public Integer search(int[] nums, int target) {
        // nums=[1,2,3,4,5],target = 4
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            // mid = (0+4)/2 = 2
            int mid = (start + end) / 2;
            // result = 2 < 4
            int result = nums[mid];
            //
            if (result == target) {
                return mid;
            } else if (result < target) {
                start = mid + 1;
            } else {
                // 2 < 4
                end = mid - 1;
            }

        }


        // not found, return -1
        return -1;


    }


}
