package org.finalframework.data.query;

import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-04 16:07:03
 * @since 1.0
 */
public interface SqlNode {
    void apply(Node parent, String expression);
}
