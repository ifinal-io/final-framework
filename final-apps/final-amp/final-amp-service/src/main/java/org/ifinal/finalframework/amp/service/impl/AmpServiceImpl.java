package org.ifinal.finalframework.amp.service.impl;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import org.apache.dubbo.config.annotation.DubboService;
import org.ifinal.finalframework.amp.client.AmpService;
import org.ifinal.finalframework.amp.dao.query.QDataSource;
import org.ifinal.finalframework.amp.entity.DataSource;
import org.ifinal.finalframework.amp.service.DataSourceService;
import org.ifinal.finalframework.data.query.Query;
import org.springframework.stereotype.Service;

/**
 * AmpServiceImpl.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@DubboService(interfaceClass = AmpService.class)
public class AmpServiceImpl implements AmpService {

    @Resource
    private DataSourceService dataSourceService;

    @Override
    public Map<String, DataSource> getDataSources(@NotEmpty final String project, @NotEmpty final String application) {
        return dataSourceService.select(new Query().where(
            QDataSource.project.eq(project), QDataSource.application.eq(application)
        ))
            .stream()
            .collect(Collectors.toMap(DataSource::getName, Function.identity()));
    }

}
