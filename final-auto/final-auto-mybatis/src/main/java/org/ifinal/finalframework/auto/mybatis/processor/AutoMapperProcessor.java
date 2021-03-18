package org.ifinal.finalframework.auto.mybatis.processor;

import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import org.apache.ibatis.annotations.Mapper;
import org.ifinal.auto.service.annotation.AutoProcessor;
import org.ifinal.auto.service.processor.AbsServiceProcessor;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoProcessor
@SuppressWarnings("unused")
@SupportedAnnotationTypes({
    "org.apache.ibatis.annotations.Mapper"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AutoMapperProcessor extends AbsServiceProcessor {

    private TypeElement mapperElement;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {

        super.init(processingEnv);
        this.mapperElement = processingEnv.getElementUtils().getTypeElement(Mapper.class.getCanonicalName());
    }

    @Override
    protected boolean doProcess(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        roundEnv.getElementsAnnotatedWith(Mapper.class)
            .forEach(mapper -> addService(mapperElement, (TypeElement) mapper));

        return false;
    }

}

