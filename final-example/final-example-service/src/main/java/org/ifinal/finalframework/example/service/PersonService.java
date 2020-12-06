package org.ifinal.finalframework.example.service;

import org.ifinal.finalframework.example.dao.mapper.PersonMapper;
import org.ifinal.finalframework.example.entity.Person;
import org.ifinal.finalframework.service.AbsService;

import javax.annotation.Generated;

/**
 * @author finalframework
 * @version 1.0.0
 */
@Generated(
    value = "org.ifinal.finalframework.auto.service.processor.AutoServiceGeneratorProcessor",
    date = "2020-12-06 18:55:05"
)
public interface PersonService extends AbsService<Long, Person, PersonMapper> {
}
