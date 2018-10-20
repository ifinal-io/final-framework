package cn.com.likly.finalframework.processor;

import cn.com.likly.finalframework.data.annotation.Entity;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
import cn.com.likly.finalframework.model.EntityModel;
import cn.com.likly.finalframework.model.EntityModelFactory;
import com.google.auto.service.AutoService;
import com.squareup.javawriter.JavaWriter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Generated;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@Slf4j
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {

    private Set<Element> entityElement = new HashSet<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Entity.class.getName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            generateEntityHolderFiles();
        } else {
            processAnnotations(annotations, roundEnv);
        }

        return true;
    }

    private void generateEntityHolderFiles() {
        entityElement.stream()
                .map(it -> EntityModelFactory.create((TypeElement) it))
                .forEach(this::generateEntityHolder);
    }

    private void generateEntityHolder(EntityModel entity) {
        try {
            Filer filer = processingEnv.getFiler();
            final String entityName = entity.getEntityName();
            final String entitySimpleName = entityName.substring(entityName.lastIndexOf(".") + 1);
            final String packageName = entityName.substring(0, entityName.lastIndexOf("."));
            final String className = entitySimpleName + "EntityHolder";
            final String resourceFile = packageName.replaceAll("\\.", "/") + "/" + className + ".java";
            logger.info("begin generate EntityHolder: {}", className);

            FileObject fileObject = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", resourceFile);
            JavaWriter jw = new JavaWriter(fileObject.openWriter());

            Map<String, String[]> generatedMap = new HashMap<>();
            generatedMap.put("value", new String[]{"\"" + EntityProcessor.class.getName() + "\""});


            jw.emitPackage(packageName)
                    .emitImports(Generated.class)
                    .emitImports(EntityHolder.class)
                    .emitImports(PropertyHolder.class)
                    .emitImports(entityName)
                    .emitAnnotation(Generated.class, generatedMap)
                    .beginType(packageName + "." + className, "class", EnumSet.of(Modifier.PUBLIC, Modifier.FINAL))
                    .emitField("EntityHolder<" + entityName + ">", "entity", EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL), "EntityHolder.from(" + entitySimpleName + ".class)");


            entity.getProperties()
                    .forEach(
                            it -> {
                                logger.info("name={}", it);
                                try {
                                    jw.emitField(
                                            "PropertyHolder",
                                            it,
                                            EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
                                            "entity.getRequiredPersistentProperty(\"" + it + "\")"
                                    );
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );

            jw.endType().close();

//            jw.
//                    .emitField("String", "firstName", EnumSet.of(Modifier.PUBLIC, ))
//                    .emitField("String", "lastName", EnumSet.of(Modifier.PRIVATE))
//                    .emitJavadoc("Return the person's full name")
//                    .beginMethod("String", "getName", EnumSet.of(Modifier.PUBLIC))
//                    .emitStatement("return firstName + \" - \" + lastName")
//                    .endMethod()
//                    .beginMethod("String", "getFirstName", EnumSet.of(Modifier.PUBLIC))
//                    .emitStatement("return firstName")
//                    .endMethod()
//                    .beginMethod("String", "getLastName", EnumSet.of(Modifier.PUBLIC))
//                    .emitStatement("return lastName") //注意不要使用分号结束return语句
//                    .endMethod()
//                    .endType()
//                    .close();
        } catch (Exception e) {
            logger.error("Create EntityHolder Exception:", e);
        }

    }

    private void processAnnotations(Set<? extends TypeElement> annotations,
                                    RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Entity.class);
        entityElement.addAll(elements);
    }


}
