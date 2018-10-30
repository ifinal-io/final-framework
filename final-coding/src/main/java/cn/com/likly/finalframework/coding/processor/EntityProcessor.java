package cn.com.likly.finalframework.coding.processor;

import cn.com.likly.finalframework.coding.coder.Coder;
import cn.com.likly.finalframework.coding.coder.FreemakerCoder;
import cn.com.likly.finalframework.coding.element.EntityFactory;
import cn.com.likly.finalframework.coding.model.Mapper;
import cn.com.likly.finalframework.coding.model.QEntity;
import cn.com.likly.finalframework.data.annotation.Entity;
import cn.com.likly.finalframework.data.annotation.MapperEntity;
import com.google.auto.service.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EntityProcessor.class);
    private final Coder coder = new FreemakerCoder();
    private Filer filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Entity.class.getName());
        types.add(MapperEntity.class.getName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateEntityFiles(roundEnv.getElementsAnnotatedWith(MapperEntity.class));
        generateEntityFiles(roundEnv.getElementsAnnotatedWith(Entity.class));
        return true;
    }

    private void generateEntityFiles(Set<? extends Element> elements) {
        elements
                .stream()
                .map(it -> EntityFactory.create(processingEnv, (TypeElement) it))
                .collect(Collectors.toList())
                .forEach(it -> {
                    final String packageName = it.getPackage();
                    final String entityName = it.getSimpleName();
                    QEntity qEntity = new QEntity(packageName.replace(".entity", ".query"), "Q" + entityName, it);
                    try {
                        coder.coding(qEntity, filer
                                .createSourceFile(qEntity.getPackage() + "." + qEntity.getName())
                                .openWriter());
                        if (it.hasAnnotation(MapperEntity.class)) {

                            final String primaryKeyType = it.getRequiredIdProperty().getType();

                            Mapper mapper = new Mapper(packageName.replace(".entity", ".dao.mapper"), entityName + "Mapper", primaryKeyType
                                    .startsWith("java.lang.") ? primaryKeyType.substring(primaryKeyType.lastIndexOf(".") + 1) : primaryKeyType, it
                                    .getPackage(), it.getSimpleName());
                            coder.coding(mapper, filer
                                    .createSourceFile(mapper.getPackage() + "." + mapper.getName())
                                    .openWriter());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });


    }


}
