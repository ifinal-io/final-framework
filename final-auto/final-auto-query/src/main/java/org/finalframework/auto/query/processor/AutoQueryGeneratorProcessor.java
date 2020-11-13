package org.finalframework.auto.query.processor;

import com.squareup.javapoet.*;
import org.finalframework.annotation.IEntity;
import org.finalframework.auto.data.EntityFactory;
import org.finalframework.auto.query.annotation.AutoQuery;
import org.finalframework.auto.service.annotation.AutoProcessor;
import org.finalframework.data.query.AbsQEntity;
import org.finalframework.data.query.QProperty;
import org.finalframework.io.support.ServicesLoader;
import org.finalframework.javapoets.JavaPoets;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * QEntity 代码生成处理器
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@SuppressWarnings("unused")
@AutoProcessor
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class AutoQueryGeneratorProcessor extends AbstractProcessor {

    private static final String DEFAULT_ENTITY_PATH = "entity";
    private static final String DEFAULT_QUERY_PATH = "dao.query";

    private Elements elementUtils;
    private Types typeUtils;

    /**
     * @see IEntity
     */
    private TypeElement entityTypeElement;

    /**
     * Lombok 注解处理器
     */
    private Processor lombokProcessor;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.entityTypeElement = elementUtils.getTypeElement(IEntity.class.getCanonicalName());
        initLombokProcessor();
    }

    private void initLombokProcessor() {
        try {
            lombokProcessor = (Processor) Class.forName("lombok.core.AnnotationProcessor").newInstance();
        } catch (Exception e) {
            // ignore
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            ServicesLoader.load(IEntity.class, getClass().getClassLoader())
                    .stream()
                    .map(entity -> processingEnv.getElementUtils().getTypeElement(entity))
                    .forEach(entity -> this.generate(entity, DEFAULT_ENTITY_PATH, DEFAULT_QUERY_PATH));
        } else {

            if (lombokProcessor != null) {
                lombokProcessor.process(annotations, roundEnv);
            }

            ElementFilter.packagesIn(roundEnv.getElementsAnnotatedWith(AutoQuery.class))
                    .forEach(packageElement -> {
                        final AutoQuery autoQuery = packageElement.getAnnotation(AutoQuery.class);

                        Arrays.stream(autoQuery.value())
                                .map(packageName -> ElementFilter.typesIn(processingEnv.getElementUtils().getPackageElement(packageName).getEnclosedElements()))
                                .forEach(entities -> entities.stream()
                                        .filter(this::isEntity)
                                        .forEach(entity -> this.generate(entity, autoQuery)));


                    });

        }


        return false;
    }

    private void generate(TypeElement entity, AutoQuery autoQuery) {
        generate(entity, autoQuery.entity(), autoQuery.query());
    }

    private void generate(TypeElement entity, String entityPath, String queryPath) {
        final String packageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName().toString()
                .replace("." + Optional.ofNullable(entityPath).orElse(DEFAULT_ENTITY_PATH), "." + Optional.ofNullable(queryPath).orElse(DEFAULT_QUERY_PATH));
        generator(QEntityFactory.create(processingEnv, packageName, EntityFactory.create(processingEnv, entity)));
    }


    private void generator(QEntity entity) {
        try {
            String name = entity.getName();
            info("try to generator entity of " + name);
            TypeElement mapperElement = processingEnv.getElementUtils().getTypeElement(name);
            final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(name);
            if (mapperElement == null) {
                try (Writer writer = sourceFile.openWriter()) {
                    JavaFile javaFile = buildJavaFile(entity);
                    javaFile.writeTo(writer);
                    writer.flush();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JavaFile buildJavaFile(QEntity entity) {

        // AbsQEntity<ID,IEntity>
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
                .addParameter(ParameterSpec.builder(ClassName.get(String.class), "table").build())
                .addStatement("super($T.class, table)", entity.getEntity().getElement())
                .build();

        FieldSpec entityField = FieldSpec.builder(
                ClassName.get(entity.getPackageName(), entity.getSimpleName()),
//                ClassName.get(entity.getEntity().getElement()),
                entity.getEntity().getSimpleName(),
                Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL
        ).initializer(String.format("new %s()", entity.getSimpleName()))
                .build();


        // public static final QProperty<T> {property} = ${entity.simpleName}.getRequiredProperty("{property.path}");
        List<FieldSpec> fieldSpecs = entity.getProperties().stream()
                .map(property -> FieldSpec.builder(
                        ParameterizedTypeName.get(ClassName.get(QProperty.class), ClassName.get(property.getElement().asType()))
                        , property.getName())
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$L.getRequiredProperty($S)", entity.getEntity().getSimpleName(), property.getPath())
                        .addJavadoc("@see $L#$L", entity.getEntity().getSimpleName(), property.getPath().replaceAll("\\.", "#"))
                        .build())
                .collect(Collectors.toList());


        TypeSpec clazz = TypeSpec.classBuilder(entity.getSimpleName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(parameterizedTypeName)
                .addMethod(defaultConstructor).addMethod(tableConstructor)
                .addAnnotation(JavaPoets.generated(AutoQueryGeneratorProcessor.class))
                .addJavadoc(JavaPoets.JavaDoc.author())
                .addJavadoc(JavaPoets.JavaDoc.version())
                .addJavadoc(JavaPoets.JavaDoc.date())
                .addField(entityField)
                .addFields(fieldSpecs)
                .build();


        return JavaFile.builder(entity.getPackageName(), clazz).indent("    ").build();
    }


    private boolean isEntity(TypeElement typeElement) {
        return isSubtype(typeElement, entityTypeElement);

    }

    private boolean isSubtype(Element subTypeElement, Element parentTypeElement) {
        return typeUtils.isSubtype(typeUtils.erasure(subTypeElement.asType()), typeUtils.erasure(parentTypeElement.asType()));
    }


    private void info(String msg) {
        if (processingEnv.getOptions().containsKey("debug")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
        }
    }


}
