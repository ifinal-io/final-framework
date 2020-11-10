package org.finalframework.auto.data.processor;


import org.finalframework.annotation.IEnum;
import org.finalframework.annotation.data.Transient;
import org.finalframework.auto.data.TypeElementFilter;
import org.finalframework.auto.service.annotation.AutoProcessor;
import org.finalframework.auto.service.processor.AbsServiceProcessor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-31 15:46:02
 * @see IEnum
 * @since 1.0
 */
@AutoProcessor
@SuppressWarnings("unused")
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AutoEnumProcessor extends AbsServiceProcessor {


    private TypeElementFilter typeElementFilter;
    private TypeElement entityElement;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.typeElementFilter = new TypeElementFilter(processingEnv, IEnum.class, Transient.class);
        this.entityElement = processingEnv.getElementUtils().getTypeElement(IEnum.class.getCanonicalName());
    }

    @Override
    protected boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        ElementFilter.typesIn(roundEnv.getRootElements())
                .stream()
                .filter(typeElementFilter::matches)
                .forEach(entity -> addService(entityElement, entity, null, "services"));


        return false;
    }
}

