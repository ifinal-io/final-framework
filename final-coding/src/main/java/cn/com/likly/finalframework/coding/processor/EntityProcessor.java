package cn.com.likly.finalframework.coding.processor;

import cn.com.likly.finalframework.coding.Coder;
import cn.com.likly.finalframework.coding.model.EntityModel;
import cn.com.likly.finalframework.coding.model.EntityModelFactory;
import cn.com.likly.finalframework.data.annotation.Entity;
import cn.com.likly.finalframework.data.annotation.MapperEntity;
import com.google.auto.service.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EntityProcessor.class);

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
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateEntityFiles(roundEnv.getElementsAnnotatedWith(MapperEntity.class));
        generateEntityFiles(roundEnv.getElementsAnnotatedWith(Entity.class));
        return true;
    }

    private void generateEntityFiles(Set<? extends Element> elements) {
        elements.stream()
                .map(it -> EntityModelFactory.create((TypeElement) it))
                .forEach(this::generateEntityHolder);
    }

    private void generateEntityHolder(EntityModel entity) {
        try {
            Filer filer = processingEnv.getFiler();
            logger.info("begin generate Entity: {}", entity.getEntityName());

            JavaFileObject holder = filer.createSourceFile(entity.getHolderPackage() + "." + entity.getHolderName());
            Coder.coding("entity/holder.ftl", entity, holder.openWriter());

            if (entity.isMapperEntity()) {
                JavaFileObject mapper = filer.createSourceFile(entity.getMapperPackage() + "." + entity.getMapperName());
                Coder.coding("entity/mapper.ftl", entity, mapper.openWriter());
            }


        } catch (Exception e) {
            logger.error("Create EntityHolder Exception:", e);
            throw new RuntimeException(e);
        }

    }

}
