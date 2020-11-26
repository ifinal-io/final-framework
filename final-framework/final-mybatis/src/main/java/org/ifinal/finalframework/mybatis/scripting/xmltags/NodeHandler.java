package org.ifinal.finalframework.mybatis.scripting.xmltags;


import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface NodeHandler {
    void handleNode(XNode nodeToHandle, List<SqlNode> targetContents);
}

