package org.ifinal.finalframework.auto.service.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.Writer;
import java.util.Set;
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
import org.ifinal.finalframework.auto.service.annotation.AutoProcessor;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.ifinal.finalframework.javapoets.JavaPoets;
import org.ifinal.finalframework.javapoets.JavaPoets.Javadoc;
import org.ifinal.finalframework.service.AbsService;
import org.ifinal.finalframework.service.AbsServiceImpl;
import org.springframework.stereotype.Service;

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
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            ServicesLoader.load(IEntity.class, getClass().getClassLoader())
                .stream()
                .map(entity -> processingEnv.getElementUtils().getTypeElement(entity))
                .forEach(this::generateService);
        }

        return false;
    }

    private void generateService(final TypeElement entity) {
        final String servicePackageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName()
            .toString()
            .replace("." + DEFAULT_ENTITY_PATH, "." + DEFAULT_SERVICE_PATH);

        final String serviceImplPackageName = servicePackageName + ".impl";

        final String serviceName = entity.getSimpleName().toString() + SERVICE_SUFFIX;
        final String serviceImplName = entity.getSimpleName().toString() + SERVICE_IMPL_SUFFIX;

        final TypeElement serviceElement = processingEnv.getElementUtils()
            .getTypeElement(servicePackageName + "." + serviceName);
        final TypeElement serviceImplElement = processingEnv.getElementUtils()
            .getTypeElement(serviceImplPackageName + "." + serviceImplName);

        generateService(entity, servicePackageName, serviceName, serviceElement);
        generateServiceImpl(entity, servicePackageName, serviceImplPackageName, serviceName, serviceImplName,
            serviceImplElement);

    }

    private void generateService(final TypeElement entity, final String servicePackageName, final String serviceName,
        final TypeElement serviceElement) {
        if (serviceElement == null) {

            try {
                final JavaFileObject sourceFile = processingEnv.getFiler()
                    .createSourceFile(servicePackageName + "." + serviceName);

                final String mapperPackageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName()
                    .toString()
                    .replace("." + DEFAULT_ENTITY_PATH, "." + DEFAULT_MAPPER_PATH);
                final String mapperName = entity.getSimpleName().toString() + MAPPER_SUFFIX;

                // AbsService<I,IEntity,EntityMapper>
                ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                    ClassName.get(AbsService.class),
                    // 偷个小懒，先写死
                    TypeName.get(Long.class),
                    ClassName.get(entity),
                    ClassName.get(mapperPackageName, mapperName)
                );

                // public interface EntityService extends AbsService<I,IEntity,EntityMapper>
                TypeSpec service = TypeSpec.interfaceBuilder(serviceName)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(parameterizedTypeName)
                    .addAnnotation(JavaPoets.generated(AutoServiceGeneratorProcessor.class))
                    .addJavadoc(Javadoc.author())
                    .addJavadoc(Javadoc.version())
                    .build();

                try (Writer writer = sourceFile.openWriter()) {
                    JavaFile javaFile = JavaFile.builder(servicePackageName, service)
                        .skipJavaLangImports(true).build();
                    javaFile.writeTo(writer);
                    writer.flush();
                }

            } catch (Exception e) {
                error(e.getMessage());
            }

        }
    }

    private void generateServiceImpl(final TypeElement entity, final String servicePackageName,
        final String serviceImplPackageName, final String serviceName, final String serviceImplName,
        final TypeElement serviceImplElement) {
        if (serviceImplElement == null) {
            try {
                final JavaFileObject sourceFile = processingEnv.getFiler()
                    .createSourceFile(serviceImplPackageName + "." + serviceImplName);

                final String mapperPackageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName()
                    .toString()
                    .replace("." + DEFAULT_ENTITY_PATH, "." + DEFAULT_MAPPER_PATH);
                final String mapperName = entity.getSimpleName().toString() + MAPPER_SUFFIX;

                // AbsServiceImpl<I,IEntity,EntityMapper>
                ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                    ClassName.get(AbsServiceImpl.class),
                    // 偷个小懒，先写死
                    TypeName.get(Long.class),
                    ClassName.get(entity),
                    ClassName.get(mapperPackageName, mapperName)
                );

                MethodSpec constructor = MethodSpec.constructorBuilder()
                    .addParameter(
                        ParameterSpec.builder(ClassName.get(mapperPackageName, mapperName), "repository")
                            .addModifiers(Modifier.FINAL).build())
                    .addStatement("super(repository)")
                    .build();

                //  class EntityServiceImpl extends AbsServiceImpl<I,IEntity,EntityMapper> implements EntityService
                TypeSpec service = TypeSpec.classBuilder(serviceImplName)
                    .superclass(parameterizedTypeName)
                    .addSuperinterface(ClassName.get(servicePackageName, serviceName))
                    .addMethod(constructor)
                    .addAnnotation(Service.class)
                    .addAnnotation(JavaPoets.generated(AutoServiceGeneratorProcessor.class))
                    .addJavadoc(Javadoc.author())
                    .addJavadoc(Javadoc.version())
                    .build();

                try (Writer writer = sourceFile.openWriter()) {
                    JavaFile javaFile = JavaFile.builder(serviceImplPackageName, service)
                        .skipJavaLangImports(true)
                        .indent("    ")
                        .build();
                    javaFile.writeTo(writer);
                    writer.flush();
                }

            } catch (Exception e) {
                error(e.getMessage());
            }
        }
    }

    private void error(final String msg) {

        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }

}
