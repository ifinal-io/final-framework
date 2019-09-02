package org.finalframework.test.service;

import org.finalframework.monitor.annotation.OperationAction;
import org.finalframework.spring.aop.annotation.OperationAttribute;
import org.finalframework.test.entity.Person;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 17:47:13
 * @since 1.0
 */
public interface PersonService {
    @OperationAction(type = 1, name = "PersonService", target = "{#id}")
    @OperationAction(type = 1, name = "PersonService", target = "{#id}", attributes = {
            @OperationAttribute(name = "name", value = "{'haha' + #id}")
    })
    Person findById(Long id);
}
