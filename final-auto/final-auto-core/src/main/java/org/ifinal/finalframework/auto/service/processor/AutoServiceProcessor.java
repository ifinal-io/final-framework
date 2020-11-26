package org.ifinal.finalframework.auto.service.processor;

import org.ifinal.finalframework.auto.model.AnnotationMirrors;
import org.ifinal.finalframework.auto.service.annotation.AutoService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@com.google.auto.service.AutoService(Processor.class)
@SupportedOptions({"debug", "verify"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class AutoServiceProcessor extends AbsServiceProcessor {

    /**
     * @see AutoService#value()
     */
    private static final String KEY_VALUE = "value";
    /**
     * @see AutoService#name()
     */
    private static final String KEY_NAME = "name";
    /**
     * @see AutoService#path()
     */
    private static final String KEY_PATH = "path";

    @Override
    protected boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final Set<TypeElement> elements = ElementFilter.typesIn(roundEnv.getRootElements());
        for (TypeElement element : elements) {
            if (element.getKind() == ElementKind.ANNOTATION_TYPE) continue;
            for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                if (annotationMirror.getAnnotationType().toString().equals(AutoService.class.getCanonicalName())) {
                    processAutoService(element, annotationMirror);
                } else {
                    List<? extends AnnotationMirror> mirrors = annotationMirror.getAnnotationType().asElement().getAnnotationMirrors();
                    for (AnnotationMirror mirror : mirrors) {
                        if (mirror.getAnnotationType().toString().equals(AutoService.class.getCanonicalName())) {
                            processAutoService(element, mirror, annotationMirror);
                        }
                    }
                }
            }
        }
        return false;

    }


    private void processAutoService(@NonNull TypeElement element, @NonNull AnnotationMirror autoService) {
        processAutoService(element, autoService, null);
    }

    private void processAutoService(@NonNull TypeElement element, @NonNull AnnotationMirror autoService, @Nullable AnnotationMirror targetService) {
        try {
            final Map<String, AnnotationValue> autoServiceValues = AnnotationMirrors.getAnnotationValues(autoService);
            final Map<String, AnnotationValue> targetServiceValues = targetService == null ? null : AnnotationMirrors.getAnnotationValues(targetService);

            final DeclaredType serviceInterface = (DeclaredType) autoServiceValues.get(KEY_VALUE).getValue();
            final String path = autoServiceValues.containsKey(KEY_PATH) ? (String) autoServiceValues.get(KEY_PATH).getValue() : "services";
            // first, the name is AutoService#name()
            String name = autoServiceValues.containsKey(KEY_NAME) ? (String) autoServiceValues.get(KEY_NAME).getValue() : null;
            if (targetServiceValues != null && targetServiceValues.containsKey(KEY_VALUE)) {
//                // the targetService annotation may do not have the value property.
                name = (String) targetServiceValues.get(KEY_VALUE).getValue();
            }
            addService((TypeElement) serviceInterface.asElement(), element, name, path);

        } catch (Exception e) {
            //ignore
            error(e.getMessage(), element, autoService);
        }

    }


}



