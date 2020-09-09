

package org.finalframework.data.repository;


import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.data.service.AbsService;
import org.springframework.beans.factory.ObjectProvider;

import java.util.HashMap;
import java.util.List;
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

    private static Map<Class<? extends Repository>, RepositoryHolder> repositoryHolders = new HashMap();

    public RepositoryManager(ObjectProvider<List<Repository<?, ?>>> repositories) {
        final List<Repository<?, ?>> list = repositories.getIfAvailable();
        list.stream()
                .filter(it -> !(it instanceof AbsService))
                .map(RepositoryHolder::from)
                .filter(Objects::nonNull)
                .forEach(it -> repositoryHolders.put(it.getRepositoryClass(), it));


        System.out.println();
    }

    public static RepositoryHolder from(Class<? extends Repository> repository) {
        return repositoryHolders.get(repository);
    }

}

