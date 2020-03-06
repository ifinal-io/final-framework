package org.finalframework.core.filler;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 14:17:35
 * @since 1.0
 */
public interface Filler<H, P> {

    boolean fill(H holder, P param);
}
