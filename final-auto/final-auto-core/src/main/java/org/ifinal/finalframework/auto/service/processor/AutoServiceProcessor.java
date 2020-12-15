package org.ifinal.finalframework.auto.service.processor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;
import org.ifinal.finalframework.auto.annotation.AutoService;
import org.ifinal.finalframework.auto.model.AnnotationMirrors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * AutoServiceProcessor.
 *
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
     * value.
     *
     * @see AutoService#value()
     */
    private static final String KEY_VALUE = "value";

    /**
     * name.
     *
     * @see AutoService#name()
     */
    private static final String KEY_NAME = "name";

    /**
     * path.
     *
     * @see AutoService#path()
     */
    private static final String KEY_PATH = "path";

    private static final IgnoreFilter ignoreFilter = new IgnoreFilter();

    @Override
    protected boolean doProcess(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        final Set<TypeElement> elements = ElementFilter.typesIn(roundEnv.getRootElements());

        elements.stream()
            .filter(ignoreFilter)
            .forEach(element -> {
                for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                    if (annotationMirror.getAnnotationType().toString().equals(AutoService.class.getCanonicalName())) {
                        processAutoService(element, annotationMirror);
                    } else {
                        final List<? extends AnnotationMirror> mirrors = annotationMirror.getAnnotationType()
                            .asElement().getAnnotationMirrors();
                        for (AnnotationMirror mirror : mirrors) {
                            if (mirror.getAnnotationType().toString().equals(AutoService.class.getCanonicalName())) {
                                processAutoService(element, mirror, annotationMirror);
                            }
                        }
                    }
                }
            });

        return false;
    }

    private void processAutoService(final @NonNull TypeElement element, final @NonNull AnnotationMirror autoService) {
        processAutoService(element, autoService, null);
    }

    private void processAutoService(final @NonNull TypeElement element, final @NonNull AnnotationMirror autoService,
        final @Nullable AnnotationMirror targetService) {

        try {
            final Map<String, AnnotationValue> autoServiceValues = AnnotationMirrors.getAnnotationValues(autoService);
            final Map<String, AnnotationValue> targetServiceValues = targetService == null
                ? null : AnnotationMirrors.getAnnotationValues(targetService);

            final DeclaredType serviceInterface = (DeclaredType) autoServiceValues.get(KEY_VALUE).getValue();
            final String path = autoServiceValues.containsKey(KEY_PATH)
                ? (String) autoServiceValues.get(KEY_PATH).getValue() : "services";
            // first, the name is AutoService#name()
            String name = autoServiceValues.containsKey(KEY_NAME)
                ? (String) autoServiceValues.get(KEY_NAME).getValue() : null;
            if (targetServiceValues != null && targetServiceValues.containsKey(KEY_VALUE)) {
                // the targetService annotation may do not have the value property.
                name = (String) targetServiceValues.get(KEY_VALUE).getValue();
            }
            addService((TypeElement) serviceInterface.asElement(), element, name, path);

        } catch (final Exception e) {
            //ignore
            error(e.getMessage(), element, autoService);
        }

    }

    private static class IgnoreFilter implements Predicate<TypeElement> {

        @Override
        public boolean test(final TypeElement element) {

            AutoService annotation = element.getAnnotation(AutoService.class);
            if (Objects.nonNull(annotation)) {
                return !annotation.ignore();
            }

            return true;
        }

    }

}



