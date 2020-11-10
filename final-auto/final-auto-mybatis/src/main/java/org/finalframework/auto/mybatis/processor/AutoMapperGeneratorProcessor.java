package org.finalframework.auto.mybatis.processor;


import org.finalframework.annotation.IEntity;
import org.finalframework.auto.coding.Coder;
import org.finalframework.auto.data.EntityFactory;
import org.finalframework.auto.mybatis.annotation.AutoMapper;
import org.finalframework.auto.service.annotation.AutoProcessor;
import org.finalframework.io.support.ServicesLoader;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * Mapper.java 生成注解处理器
 *
 * @author likly
 * @version 1.0
 * @date 2019-10-26 12:59:10
 * @since 1.0
 */
@SuppressWarnings("unused")
@AutoProcessor
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class AutoMapperGeneratorProcessor extends AbstractProcessor {
    private static final String MAPPER_PREFIX = "Abs";
    private static final String MAPPER_SUFFIX = "Mapper";
    private static final String DEFAULT_ENTITY_PATH = "entity";
    private static final String DEFAULT_MAPPER_PATH = "dao.mapper";
    private final Coder coder = Coder.getDefaultCoder();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            ServicesLoader.load(IEntity.class, getClass().getClassLoader())
                    .stream()
                    .map(entity -> processingEnv.getElementUtils().getTypeElement(entity))
                    .forEach(entity -> this.generate(entity, DEFAULT_ENTITY_PATH, DEFAULT_MAPPER_PATH));
        } else {
            ElementFilter.packagesIn(roundEnv.getElementsAnnotatedWith(AutoMapper.class))
                    .forEach(packageElement -> {
                        final AutoMapper autoMapper = packageElement.getAnnotation(AutoMapper.class);
                        Arrays.stream(autoMapper.value())
                                .map(packageName -> ElementFilter.typesIn(processingEnv.getElementUtils().getPackageElement(packageName).getEnclosedElements()))
                                .forEach(entities -> entities.forEach(entity -> this.generate(entity, autoMapper)));
                    });
        }
        return false;
    }

    private void generate(TypeElement entity, AutoMapper mapper) {
        generate(entity, mapper.entity(), mapper.mapper());
    }

    private void generate(TypeElement entity, String entityPath, String mapperPath) {
        final String packageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName().toString()
                .replace("." + Optional.ofNullable(entityPath).orElse(DEFAULT_ENTITY_PATH), "." + Optional.ofNullable(mapperPath).orElse(DEFAULT_MAPPER_PATH));
        String mapperName = entity.getSimpleName().toString() + MAPPER_SUFFIX;
        String absMapperName = MAPPER_PREFIX + mapperName;
        boolean inner = false;
        generator(
                Mapper.builder()
                        .packageName(packageName)
                        .simpleName(inner ? absMapperName : mapperName)
                        .inner(inner)
                        .entity(EntityFactory.create(processingEnv, entity))
                        .build()
        );
    }

    private void generator(Mapper mapper) {
        try {
            TypeElement mapperElement = processingEnv.getElementUtils().getTypeElement(mapper.getName());
            if (mapperElement == null) {
                coder.coding(mapper, processingEnv.getFiler().createSourceFile(mapper.getName()).openWriter());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }


}

