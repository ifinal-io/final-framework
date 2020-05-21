package org.finalframework.spiriter.jdbc.service;

import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 12:36:07
 * @since 1.0
 */
public interface DataSourceService {

    @Nullable
    Map<String, DataSource> getDataSources();

    @Nullable
    default DataSource getDataSource(String name) {
        return Optional.ofNullable(getDataSources()).orElse(Collections.emptyMap()).get(name);
    }


}
