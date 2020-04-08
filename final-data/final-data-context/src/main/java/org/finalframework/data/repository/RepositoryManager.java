package org.finalframework.data.repository;


import org.finalframework.data.service.AbsService;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.beans.factory.ObjectProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-06 15:33:23
 * @since 1.0
 */
@SpringComponent
public class RepositoryManager {

    private Map<Class<? extends Repository>, RepositoryHolder> repositoryHolders = new HashMap();

    public RepositoryManager(ObjectProvider<Repository<?, ?>> repositories) {
        repositories.stream()
                .filter(it -> !(it instanceof AbsService))
                .map(RepositoryHolder::from)
                .filter(Objects::nonNull)
                .forEach(it -> repositoryHolders.put(it.getRepositoryClass(), it));


        System.out.println();
    }
}

