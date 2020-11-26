package org.ifinal.finalframework.spiriter.jdbc.service;

import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public interface DataSourceService {

    @Nullable
    Map<String, DataSource> getDataSources();

    @Nullable
    default DataSource getDataSource(String name) {
        return Optional.ofNullable(getDataSources()).orElse(Collections.emptyMap()).get(name);
    }


}
