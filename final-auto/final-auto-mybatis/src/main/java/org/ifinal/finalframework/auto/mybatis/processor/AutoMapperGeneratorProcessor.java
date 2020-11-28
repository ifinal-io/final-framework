package org.ifinal.finalframework.auto.mybatis.processor;

import com.squareup.javapoet.*;
import org.ifinal.finalframework.annotation.IEntity;
import org.ifinal.finalframework.auto.data.EntityFactory;
import org.ifinal.finalframework.auto.mybatis.annotation.AutoMapper;
import org.ifinal.finalframework.auto.service.annotation.AutoProcessor;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.ifinal.finalframework.javapoets.JavaPoets;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * Mapper.java 生成注解处理器
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

        generator(
                Mapper.builder()
                        .packageName(packageName)
                        .simpleName(mapperName)
                        .inner(false)
                        .entity(EntityFactory.create(processingEnv, entity))
                        .build()
        );
    }

    private void generator(Mapper mapper) {
        try {
            final TypeElement mapperElement = processingEnv.getElementUtils().getTypeElement(mapper.getName());

            if (mapperElement == null) {
                final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(mapper.getName());


                try (Writer writer = sourceFile.openWriter()) {
                    JavaFile javaFile = buildJavaFile(mapper);
                    javaFile.writeTo(writer);
                    writer.flush();
                }

            }

        } catch (IOException e) {
            error(e.getMessage());
        }
    }

    private JavaFile buildJavaFile(Mapper mapper) {

        // AbsMapper<ID,IEntity>
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                ClassName.get(AbsMapper.class),
                TypeName.get(mapper.getEntity().getRequiredIdProperty().getType()),
                ClassName.get(mapper.getEntity().getElement())
        );


        // public interface EntityMapper extends AbsMapper<ID,IEntity>
        TypeSpec myMapper = TypeSpec.interfaceBuilder(mapper.getSimpleName())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(parameterizedTypeName)
                .addAnnotation(org.apache.ibatis.annotations.Mapper.class)
                .addAnnotation(JavaPoets.generated(AutoMapperGeneratorProcessor.class))
                .addJavadoc(JavaPoets.JavaDoc.author())
                .addJavadoc(JavaPoets.JavaDoc.version())
//                .addJavadoc(JavaPoets.JavaDoc.date())
                .build();

        return JavaFile.builder(mapper.getPackageName(), myMapper).build();

    }


    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }


}

