package org.finalframework.test.service;

import org.finalframework.cache.annotation.Cacheable;
import org.finalframework.data.service.AbsService;
import org.finalframework.monitor.annotation.MonitorAction;
import org.finalframework.test.dao.mapper.PersonMapper;
import org.finalframework.test.entity.Person;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 13:58:09
 * @since 1.0
 */
public interface PersonService extends AbsService<Long, Person, PersonMapper> {

    @Cacheable(key = "person:{#id}")
    @MonitorAction(name = "查询Person", target = "{#id}")
    default Person findById(Long id) {
        return selectOne(id);
    }

}
