package org.ifinal.finalframework.auto.query.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.auto.data.EntityFactory;
import org.ifinal.finalframework.auto.service.annotation.AutoProcessor;
import org.ifinal.finalframework.data.query.AbsQEntity;
import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.ifinal.finalframework.javapoets.JavaPoets;
import org.ifinal.finalframework.javapoets.JavaPoets.Javadoc;

/**
 * QEntity 代码生成处理器
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoProcessor
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class AutoQueryGeneratorProcessor extends AbstractProcessor {

    private static final String DEFAULT_ENTITY_PATH = "entity";

    private static final String DEFAULT_QUERY_PATH = "dao.query";

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            ServicesLoader.load(IEntity.class, getClass().getClassLoader())
                .stream()
                .map(entity -> processingEnv.getElementUtils().getTypeElement(entity))
                .forEach(this::generate);
        }

        return false;
    }

    private void generate(final TypeElement entity) {

        final String packageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName().toString()
            .replace("." + DEFAULT_ENTITY_PATH, "." + DEFAULT_QUERY_PATH);

        generator(QEntityFactory.create(processingEnv, packageName, EntityFactory.create(processingEnv, entity)));
    }

    private void generator(final QEntity entity) {

        try {
            String name = entity.getName();
            info("try to generator entity of " + name);
            TypeElement mapperElement = processingEnv.getElementUtils().getTypeElement(name);
            if (mapperElement == null) {
                final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(name);

                try (Writer writer = sourceFile.openWriter()) {
                    JavaFile javaFile = buildJavaFile(entity);
                    javaFile.writeTo(writer);
                    writer.flush();
                }

            }

        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

    private void info(final String msg) {

        if (processingEnv.getOptions().containsKey("debug")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
        }
    }

    private JavaFile buildJavaFile(final QEntity entity) {

        // AbsQEntity<I,IEntity>
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
            ClassName.get(AbsQEntity.class),
            TypeName.get(entity.getEntity().getRequiredIdProperty().getType()),
            ClassName.get(entity.getEntity().getElement())
        );

        MethodSpec defaultConstructor = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addStatement("super($T.class)", entity.getEntity().getElement())
            .build();

        MethodSpec tableConstructor = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            // final String table
            .addParameter(
                ParameterSpec.builder(ClassName.get(String.class), "table").addModifiers(Modifier.FINAL).build())
            // super(Entity.class, table)
            .addStatement("super($T.class, table)", entity.getEntity().getElement())
            .build();

        FieldSpec entityField = FieldSpec.builder(
            ClassName.get(entity.getPackageName(), entity.getSimpleName()),
            entity.getEntity().getSimpleName(),
            Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL
        ).initializer(String.format("new %s()", entity.getSimpleName()))
            .build();

        List<FieldSpec> fieldSpecs = entity.getProperties().stream()
            .map(property -> FieldSpec.builder(
                ParameterizedTypeName.get(
                    ClassName.get(QProperty.class), TypeName.get(property.getElement().asType())), property.getName())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                // Entity.getRequiredProperty('path')
                .initializer("$L.getRequiredProperty($S)", entity.getEntity().getSimpleName(), property.getPath())
                .build())
            .collect(Collectors.toList());

        TypeSpec clazz = TypeSpec.classBuilder(entity.getSimpleName())
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(parameterizedTypeName)
            .addMethod(defaultConstructor).addMethod(tableConstructor)
            .addAnnotation(JavaPoets.generated(AutoQueryGeneratorProcessor.class))
            .addJavadoc(Javadoc.author())
            .addJavadoc(Javadoc.version())
            .addField(entityField)
            .addFields(fieldSpecs)
            .build();

        return JavaFile.builder(entity.getPackageName(), clazz)
            .skipJavaLangImports(true)
            .indent("    ").build();
    }

}
