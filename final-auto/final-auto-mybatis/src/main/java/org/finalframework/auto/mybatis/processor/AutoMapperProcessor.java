package org.finalframework.auto.mybatis.processor;


import org.apache.ibatis.annotations.Mapper;
import org.finalframework.auto.service.annotation.AutoProcessor;
import org.finalframework.auto.service.processor.AbsServiceProcessor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-31 18:43:44
 * @since 1.0
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
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.mapperElement = processingEnv.getElementUtils().getTypeElement(Mapper.class.getCanonicalName());
    }

    @Override
    protected boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(Mapper.class)
                .forEach(mapper -> addService(mapperElement, (TypeElement) mapper));


        return false;
    }
}

