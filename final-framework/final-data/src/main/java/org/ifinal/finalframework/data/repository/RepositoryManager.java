package org.ifinal.finalframework.data.repository;


import org.ifinal.finalframework.data.service.AbsService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class RepositoryManager {

    private static Map<Class<? extends Repository>, RepositoryHolder> repositoryHolders = new HashMap();

    public RepositoryManager(ObjectProvider<Repository<?, ?>> repositories) {
        repositories.stream()
                .filter(it -> !(it instanceof AbsService))
                .map(RepositoryHolder::from)
                .filter(Objects::nonNull)
                .forEach(it -> repositoryHolders.put(it.getRepositoryClass(), it));


    }

    public static RepositoryHolder from(Class<? extends Repository> repository) {
        return repositoryHolders.get(repository);
    }

}

