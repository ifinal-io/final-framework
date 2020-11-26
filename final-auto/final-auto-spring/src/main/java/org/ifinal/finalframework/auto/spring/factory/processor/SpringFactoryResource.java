package org.ifinal.finalframework.auto.spring.factory.processor;


import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.lang.model.element.TypeElement;
import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * {@code spring.factories} resource.
 *
 * <pre class="code">
 *     springFactoryInterface=\
 *     springFactoryInstanceA\
 *     springFactoryInstanceB
 * </pre>
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
final class SpringFactoryResource implements Serializable {

    public static final String RESOURCE_FILE = "META-INF/spring.factories";

    private final MultiValueMap<String, String> springFactories = new LinkedMultiValueMap<>();

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


    void writeFactoryFile(OutputStream output)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, UTF_8));

        for (Map.Entry<String, List<String>> stringListEntry : springFactories.entrySet()) {
            String factory = stringListEntry.getKey();
            List<String> instances = stringListEntry.getValue();
            // # factory
            writer.write("# ");
            writer.write(factory);
            writer.newLine();
            // factory=\
            writer.write(factory);
            writer.write("=\\");
            writer.newLine();

            for (int i = 0; i < instances.size(); i++) {
                final String intance = instances.get(i);
                writer.write(intance);
                if (i != instances.size() - 1) {
                    writer.write(",\\");
                }
                writer.newLine();
            }
        }

        writer.flush();

    }

}

