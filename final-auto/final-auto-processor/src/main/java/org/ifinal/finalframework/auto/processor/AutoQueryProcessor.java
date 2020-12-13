package org.ifinal.finalframework.auto.processor;

import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import org.ifinal.finalframework.auto.service.annotation.AutoProcessor;
import org.ifinal.finalframework.auto.service.processor.AbsServiceProcessor;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoProcessor
@SuppressWarnings("unused")
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AutoQueryProcessor extends AbsServiceProcessor {

    private static final String QUERY = "org.ifinal.finalframework.annotation.core.IQuery";

    private static final String TRANSIENT = "org.ifinal.finalframework.annotation.data.Transient";

    private TypeElementFilter typeElementFilter;

    private TypeElement typeElement;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {

        super.init(processingEnv);
        this.typeElement = processingEnv.getElementUtils().getTypeElement(QUERY);
        this.typeElementFilter = new TypeElementFilter(processingEnv, typeElement, processingEnv.getElementUtils().getTypeElement(TRANSIENT));
    }

    @Override
    protected boolean doProcess(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        ElementFilter.typesIn(roundEnv.getRootElements())
            .stream()
            .filter(typeElementFilter::matches)
            .forEach(entity -> addService(typeElement, entity, null, "services"));

        return false;
    }

}

