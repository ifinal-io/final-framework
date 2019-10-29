package org.finalframework.coding.mapper;


import com.google.auto.service.AutoService;
import org.finalframework.coding.Coder;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * Mapper.java 生成注解处理器
 *
 * @author likly
 * @version 1.0
 * @date 2019-10-26 12:59:10
 * @since 1.0
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MapperGeneratorProcessor extends AbstractProcessor {

    private static final Coder coder = Coder.getDefaultCoder();

    private Elements elementUtils;

    private MapperGenerator mapperGenerator;

    private boolean entitiesProcessed = false;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
        this.mapperGenerator = new MapperGenerator(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processEntities();
        return false;
    }
    private void processEntities() {

        if (entitiesProcessed) return;

        try {
            FileObject resource = processingEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT, "", "final.entities");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                mapperGenerator.generate(elementUtils.getTypeElement(line.trim()));
            }
            reader.close();
            resource.delete();

        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "---------------");
        } finally {
            entitiesProcessed = true;
        }
    }
}

