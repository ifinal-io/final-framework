package org.ifinal.finalframework.auto.service.processor;

import com.squareup.javapoet.*;
import org.ifinal.finalframework.annotation.IEntity;
import org.ifinal.finalframework.auto.service.annotation.AutoProcessor;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.ifinal.finalframework.javapoets.JavaPoets;
import org.ifinal.finalframework.service.AbsService;
import org.ifinal.finalframework.service.AbsServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoProcessor
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AutoServiceGeneratorProcessor extends AbstractProcessor {

    private static final String MAPPER_SUFFIX = "Mapper";
    private static final String DEFAULT_MAPPER_PATH = "dao.mapper";

    private static final String SERVICE_SUFFIX = "Service";
    private static final String SERVICE_IMPL_SUFFIX = "ServiceImpl";

    private static final String DEFAULT_ENTITY_PATH = "entity";
    private static final String DEFAULT_SERVICE_PATH = "service";


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            ServicesLoader.load(IEntity.class, getClass().getClassLoader())
                    .stream()
                    .map(entity -> processingEnv.getElementUtils().getTypeElement(entity))
                    .forEach(this::generateService);
        }


        return false;
    }

    private void generateService(TypeElement entity) {
        final String servicePackageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName().toString()
                .replace("." + DEFAULT_ENTITY_PATH, "." + DEFAULT_SERVICE_PATH);

        final String serviceImplPackageName = servicePackageName + ".impl";

        final String serviceName = entity.getSimpleName().toString() + SERVICE_SUFFIX;
        final String serviceImplName = entity.getSimpleName().toString() + SERVICE_IMPL_SUFFIX;

        final TypeElement serviceElement = processingEnv.getElementUtils().getTypeElement(servicePackageName + "." + serviceName);
        final TypeElement serviceImplElement = processingEnv.getElementUtils().getTypeElement(servicePackageName + "." + serviceImplName);

        if (serviceElement == null) {

            try {
                final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(servicePackageName + "." + serviceName);

                final String mapperPackageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName().toString()
                        .replace("." + DEFAULT_ENTITY_PATH, "." + DEFAULT_MAPPER_PATH);
                final String mapperName = entity.getSimpleName().toString() + MAPPER_SUFFIX;

                // AbsService<ID,IEntity,EntityMapper>
                ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                        ClassName.get(AbsService.class),
                        TypeName.get(Long.class),// 偷个小懒，先写死
                        ClassName.get(entity),
                        ClassName.get(mapperPackageName, mapperName)
                );

                // public interface EntityService extends AbsService<ID,IEntity,EntityMapper>
                TypeSpec service = TypeSpec.interfaceBuilder(serviceName)
                        .addModifiers(Modifier.PUBLIC)
                        .addSuperinterface(parameterizedTypeName)
                        .addAnnotation(JavaPoets.generated(AutoServiceGeneratorProcessor.class))
                        .addJavadoc(JavaPoets.JavaDoc.author())
                        .addJavadoc(JavaPoets.JavaDoc.version())
                        .build();


                try (Writer writer = sourceFile.openWriter()) {
                    JavaFile javaFile = JavaFile.builder(servicePackageName, service).build();
                    javaFile.writeTo(writer);
                    writer.flush();
                }


            } catch (Exception e) {
                error(e.getMessage());
            }


        }

        if (serviceImplElement == null) {
            try {
                final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(serviceImplPackageName + "." + serviceImplName);

                final String mapperPackageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName().toString()
                        .replace("." + DEFAULT_ENTITY_PATH, "." + DEFAULT_MAPPER_PATH);
                final String mapperName = entity.getSimpleName().toString() + MAPPER_SUFFIX;

//                 AbsServiceImpl<ID,IEntity,EntityMapper>
                ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                        ClassName.get(AbsServiceImpl.class),
                        TypeName.get(Long.class), // 偷个小懒，先写死
                        ClassName.get(entity),
                        ClassName.get(mapperPackageName, mapperName)
                );

                MethodSpec constructor = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ParameterSpec.builder(ClassName.get(mapperPackageName, mapperName), "repository").build())
                        .addStatement("super(repository);")
                        .build();

                //  class EntityServiceImpl extends AbsServiceImpl<ID,IEntity,EntityMapper> implements EntityService
                TypeSpec service = TypeSpec.classBuilder(serviceImplName)
//                        .addModifiers(Modifier.PUBLIC)
                        .superclass(parameterizedTypeName)
                        .addSuperinterface(ClassName.get(servicePackageName, serviceName))
                        .addMethod(constructor)
                        .addAnnotation(Service.class)
                        .addAnnotation(JavaPoets.generated(AutoServiceGeneratorProcessor.class))
                        .addJavadoc(JavaPoets.JavaDoc.author())
                        .addJavadoc(JavaPoets.JavaDoc.version())
                        .build();


                try (Writer writer = sourceFile.openWriter()) {
                    JavaFile javaFile = JavaFile.builder(serviceImplPackageName, service).build();
                    javaFile.writeTo(writer);
                    writer.flush();
                }


            } catch (Exception e) {
                error(e.getMessage());
            }
        }


    }


    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }


}
