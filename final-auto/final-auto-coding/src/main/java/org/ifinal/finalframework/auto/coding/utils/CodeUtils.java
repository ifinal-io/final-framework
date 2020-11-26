package org.ifinal.finalframework.auto.coding.utils;


import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class CodeUtils {
    private final Elements elements;
    private final Types types;

    public CodeUtils(ProcessingEnvironment pe) {
        this.elements = pe.getElementUtils();
        this.types = pe.getTypeUtils();
    }

    public static VariableElement findField(@NonNull TypeElement clazz, @NonNull String fieldName, @Nullable TypeMirror type) {
        return ElementFilter.fieldsIn(clazz.getEnclosedElements())
                .stream()
                .filter(it -> it.getSimpleName().toString().contains(fieldName))
                .findFirst()
                .get();
    }

    public static List<VariableElement> fields(TypeElement element) {
        return ElementFilter.fieldsIn(element.getEnclosedElements());
    }

    public static List<ExecutableElement> methods(TypeElement element) {
        return ElementFilter.methodsIn(element.getEnclosedElements());
    }
}

