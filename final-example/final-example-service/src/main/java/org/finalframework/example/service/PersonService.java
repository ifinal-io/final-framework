package org.finalframework.example.service;

import org.finalframework.data.service.AbsService;
import org.finalframework.example.dao.mapper.PersonMapper;
import org.finalframework.example.entity.Person;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/20 16:50:44
 * @since 1.0
 */
public interface PersonService extends AbsService<Long, Person, PersonMapper> {


}
