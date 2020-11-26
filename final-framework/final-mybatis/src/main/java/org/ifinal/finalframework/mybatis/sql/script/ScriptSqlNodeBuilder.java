package org.ifinal.finalframework.mybatis.sql.script;

import org.springframework.lang.NonNull;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ScriptSqlNodeBuilder {

    void build(@NonNull Node script, @NonNull String value);
}
