package org.ifinal.finalframework.auto.mybatis.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.auto.data.Entity;
import org.ifinal.finalframework.auto.data.EntityFactory;
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
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * Mapper.java 生成注解处理器
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoProcessor
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class AutoMapperGeneratorProcessor extends AbstractProcessor {
    private static final String MAPPER_SUFFIX = "Mapper";
    private static final String DEFAULT_ENTITY_PATH = "entity";
    private static final String DEFAULT_MAPPER_PATH = "dao.mapper";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            ServicesLoader.load(IEntity.class, getClass().getClassLoader())
                    .stream()
                    .map(entity -> processingEnv.getElementUtils().getTypeElement(entity))
                    .forEach(this::generate);
        }
        return false;
    }

    private void generate(TypeElement entity) {
        final String packageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName().toString()
                .replace("." + DEFAULT_ENTITY_PATH, "." + DEFAULT_MAPPER_PATH);
        String mapperName = entity.getSimpleName().toString() + MAPPER_SUFFIX;

        final String elementName = packageName + "." + mapperName;

        try {
            final TypeElement mapperElement = processingEnv.getElementUtils().getTypeElement(elementName);

            if (mapperElement == null) {
                final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(elementName);


                try (Writer writer = sourceFile.openWriter()) {
                    JavaFile javaFile = buildJavaFile(packageName, mapperName, entity);
                    javaFile.writeTo(writer);
                    writer.flush();
                }

            }

        } catch (IOException e) {
            error(e.getMessage());
        }
    }

    private JavaFile buildJavaFile(String packageName, String mapperName, TypeElement typeElement) {

        Entity entity = EntityFactory.create(processingEnv, typeElement);

        // AbsMapper<I,IEntity>
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                ClassName.get(AbsMapper.class),
                TypeName.get(entity.getRequiredIdProperty().getType()),
                ClassName.get(entity.getElement())
        );


        // public interface EntityMapper extends AbsMapper<I,IEntity>
        TypeSpec myMapper = TypeSpec.interfaceBuilder(mapperName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(parameterizedTypeName)
                .addAnnotation(org.apache.ibatis.annotations.Mapper.class)
                .addAnnotation(JavaPoets.generated(AutoMapperGeneratorProcessor.class))
                .addJavadoc(JavaPoets.JavaDoc.author())
                .addJavadoc(JavaPoets.JavaDoc.version())
                .build();

        return JavaFile.builder(packageName, myMapper).build();

    }


    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }


}

