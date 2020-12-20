package org.ifinal.finalframework.amp.service.impl;

import org.ifinal.finalframework.amp.dao.mapper.DataSourceMapper;
import org.ifinal.finalframework.amp.entity.DataSource;
import org.ifinal.finalframework.amp.service.DataSourceService;
import org.ifinal.finalframework.service.AbsServiceImpl;
import org.springframework.stereotype.Service;

/**
 * DataSourceServiceImpl.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class DataSourceServiceImpl extends AbsServiceImpl<Long, DataSource, DataSourceMapper> implements DataSourceService {

    protected DataSourceServiceImpl(final DataSourceMapper repository) {
        super(repository);
    }

}
