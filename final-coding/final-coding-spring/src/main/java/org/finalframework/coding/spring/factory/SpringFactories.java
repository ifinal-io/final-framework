package org.finalframework.coding.spring.factory;


import org.finalframework.coding.annotation.Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

    private MultiValueMap<String, String> springFactories = new LinkedMultiValueMap<>();

    public void addSpringFactories(TypeElement clazz, Collection<TypeElement> elements) {
        elements.forEach(item -> addSpringFactory(clazz, item));
    }

    public void addSpringFactory(TypeElement clazz, TypeElement element) {
        this.addSpringFactory(clazz.getQualifiedName().toString(), element.getQualifiedName().toString());
    }

    public void addSpringFactory(String factoryClass, String factoryName) {
        List<String> factories = this.springFactories.get(factoryClass);
        if (factories == null || !factories.contains(factoryName)) {
            this.springFactories.add(factoryClass, factoryName);
        }
    }

    public MultiValueMap<String, String> getSpringFactories() {
        return springFactories;
    }

}

