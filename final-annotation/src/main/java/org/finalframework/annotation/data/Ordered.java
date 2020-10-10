package org.finalframework.annotation.data;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/10 10:48:31
 * @since 1.0
 */
interface Ordered {
    int ID = Integer.MIN_VALUE;

    int VERSION = Integer.MAX_VALUE - 200;

    int CREATOR = Integer.MAX_VALUE - 130;
    int CREATED = Integer.MAX_VALUE - 120;

    int LAST_MODIFIER = Integer.MAX_VALUE - 110;
    int LAST_MODIFIED = Integer.MAX_VALUE - 100;

    int YN = Integer.MAX_VALUE;
}
