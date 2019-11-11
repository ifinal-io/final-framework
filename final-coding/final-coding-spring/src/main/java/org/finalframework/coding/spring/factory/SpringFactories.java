package org.finalframework.coding.spring.factory;


import org.finalframework.coding.annotation.Template;

import javax.lang.model.element.TypeElement;
import java.io.Serializable;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 09:28:20
 * @since 1.0
 */
@Template("spring/spring.factories.vm")
public class SpringFactories implements Serializable {

    private Map<TypeElement, Set<TypeElement>> springFactories = new LinkedHashMap<>();

    public void addSpringFactories(TypeElement clazz, Collection<TypeElement> elements) {
        elements.forEach(item -> addSpringFactory(clazz, item));
    }

    public void addSpringFactory(TypeElement clazz, TypeElement element) {
        Set<TypeElement> elementSet = springFactories.getOrDefault(clazz, new HashSet<>());
        elementSet.add(element);
        springFactories.put(clazz, elementSet);
    }

    public Map<TypeElement, Set<TypeElement>> getSpringFactories() {
        return springFactories;
    }

}

