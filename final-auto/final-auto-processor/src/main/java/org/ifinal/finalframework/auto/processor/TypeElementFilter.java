package org.ifinal.finalframework.auto.processor;


import org.ifinal.finalframework.util.function.Filter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class TypeElementFilter implements Filter<TypeElement> {


    private final Types typeUtils;
    private final Messager messager;

    private final TypeElement entityTypeElement;
    private final TypeElement transientAnnotationTypeElement;

    public TypeElementFilter(@NonNull ProcessingEnvironment processingEnvironment,
                             @NonNull TypeElement entityTypeElement,
                             @Nullable TypeElement transientAnnotationTypeElement) {
        this.typeUtils = processingEnvironment.getTypeUtils();
        this.messager = processingEnvironment.getMessager();
        this.entityTypeElement = entityTypeElement;
        this.transientAnnotationTypeElement = transientAnnotationTypeElement;
    }

    @Override
    public boolean matches(TypeElement typeElement) {
        // 忽略抽象的类
//        if (typeElement.getModifiers().contains(Modifier.ABSTRACT)) {
//            return false;
//        }
        //忽略被注解不解析的类
        if (isAnnotated(typeElement, transientAnnotationTypeElement)) {
            return false;
        }

        boolean subtype = typeUtils.isSubtype(typeUtils.erasure(typeElement.asType()), typeUtils.erasure(entityTypeElement.asType()));
        if (subtype) {
            String msg = "[INFO] [EntityFilter] find entity : " + typeElement.getQualifiedName().toString();
            System.out.println(msg);
            messager.printMessage(Diagnostic.Kind.NOTE, msg);
        }
        return subtype;
    }

    private boolean isAnnotated(@NonNull TypeElement element, @Nullable TypeElement annotationTypeElement) {
        if (Objects.isNull(annotationTypeElement)) {
            return false;
        }

        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (typeUtils.isSameType(annotationMirror.getAnnotationType(), annotationTypeElement.asType())) {
                return true;
            }
        }


        return false;


    }


}

