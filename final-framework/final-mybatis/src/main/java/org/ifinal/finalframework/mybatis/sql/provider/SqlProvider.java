package org.ifinal.finalframework.mybatis.sql.provider;

import java.util.Map;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SqlProvider extends ProviderMethodResolver {

    String provide(ProviderContext context, Map<String, Object> parameters);

}
