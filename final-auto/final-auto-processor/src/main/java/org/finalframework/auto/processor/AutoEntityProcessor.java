package org.finalframework.auto.processor;


import org.finalframework.auto.service.annotation.AutoProcessor;
import org.finalframework.auto.service.processor.AbsServiceProcessor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <pre class="code">
 *     ServicesLoader.load(IEntity.class,getClass().getClassLoader())
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-08-31 15:46:02
 * @see java.util.ServiceLoader
 * @since 1.0
 */
@AutoProcessor
@SuppressWarnings("unused")
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AutoEntityProcessor extends AbsServiceProcessor {

    private static final String IENTITY = "org.finalframework.annotation.IEntity";
    private static final String TRANSIENT = "org.finalframework.annotation.data.Transient";


    private TypeElementFilter typeElementFilter;
    private TypeElement entityElement;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.entityElement = processingEnv.getElementUtils().getTypeElement(IENTITY);
        this.typeElementFilter = new TypeElementFilter(processingEnv, entityElement, processingEnv.getElementUtils().getTypeElement(TRANSIENT));
    }

    @Override
    protected boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        ElementFilter.typesIn(roundEnv.getRootElements())
                .stream()
                .filter(typeElementFilter::matches)
                .forEach(entity -> addService(entityElement, entity));


        return false;
    }

}

