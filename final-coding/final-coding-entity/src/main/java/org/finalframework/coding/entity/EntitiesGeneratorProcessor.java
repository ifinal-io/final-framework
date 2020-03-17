package org.finalframework.coding.entity;

import com.google.auto.service.AutoService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import org.finalframework.coding.Coder;
import org.finalframework.coding.entity.validator.EntityValidator;
import org.finalframework.coding.entity.validator.EnumValidator;
import org.finalframework.core.configuration.Configuration;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.annotation.Transient;
import org.finalframework.data.mapping.converter.NameConverterRegistry;

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

    private List<ElementVisitor<?, ?>> elementVisitors = new ArrayList<>(8);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Configuration.getInstance().load(processingEnv);
        NameConverterRegistry.getInstance().reload();
        this.entityFilter = new EntityFilter(processingEnv);
        this.entitiesHelper = new EntitiesHelper(processingEnv);

        this.elementVisitors.add(new EnumValidator(processingEnv, IEnum.class));
        this.elementVisitors.add(new EntityValidator(processingEnv, IEntity.class));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getRootElements().forEach(it -> {
            elementVisitors.forEach(elementVisitor -> it.accept(elementVisitor, null));
        });

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
