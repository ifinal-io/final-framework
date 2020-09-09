

package org.finalframework.mybatis.sql.script;

import org.springframework.lang.NonNull;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 21:29:53
 * @since 1.0
 */
public interface ScriptSqlNodeBuilder {

    void build(@NonNull Node script, @NonNull String value);
}
