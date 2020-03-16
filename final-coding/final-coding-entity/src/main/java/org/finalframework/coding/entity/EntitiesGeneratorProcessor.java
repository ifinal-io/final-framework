package org.finalframework.coding.entity;

import com.google.auto.service.AutoService;
import org.finalframework.coding.Coder;
import org.finalframework.core.configuration.Configuration;
import org.finalframework.data.annotation.Transient;
import org.finalframework.data.mapping.converter.NameConverterRegistry;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.HashSet;
import java.util.Set;

/**
 * QEntity 代码生成处理器
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@SuppressWarnings("unused")
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EntitiesGeneratorProcessor extends AbstractProcessor {
    private Coder coder = Coder.getDefaultCoder();
    private Set<TypeElement> entities = new HashSet<>(128);
    private EntityFilter entityFilter;
    private EntitiesHelper entitiesHelper;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Configuration.getInstance().load(processingEnv);
        NameConverterRegistry.getInstance().reload();
        this.entityFilter = new EntityFilter(processingEnv);
        this.entitiesHelper = new EntitiesHelper(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {

//            entities.forEach(entity -> {
//                Bean.from(processingEnv, entity)
//                        .forEach(property -> {
//                            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, property.toString());
//                        });
//
//            });

            coding(Entities.builder().addEntities(entities).build());
        } else {
            ElementFilter.typesIn(roundEnv.getRootElements())
                    .stream()
                    .filter(it -> it.getAnnotation(Transient.class) == null)
                    .filter(entityFilter::matches)
                    .forEach(entities::add);
        }
        return false;
    }

    private void coding(Entities entities) {
        entitiesHelper.generate(entities);
    }


}
