package org.ifinal.finalframework.mybatis.sql.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SqlProvider extends ProviderMethodResolver {
    String provide(ProviderContext context, Map<String, Object> parameters);
}
