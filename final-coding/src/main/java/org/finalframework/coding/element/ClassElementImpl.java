package org.finalframework.coding.element;

import org.springframework.data.util.Lazy;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-12 19:15:01
 * @since 1.0
 */
public class ClassElementImpl implements ClassElement {
    private final TypeElement typeElement;
    private final Lazy<ClassElement> superClassElement;
    public ClassElementImpl(TypeElement typeElement) {
        this.typeElement = typeElement;
        this.superClassElement = Lazy.of(() -> {
            TypeMirror superclass = typeElement.getSuperclass();
            if (superclass != null) {
                if (superclass.getKind() == TypeKind.NONE) {
                    return null;
                } else {
                    new ClassElementImpl((TypeElement) ((DeclaredType) superclass).asElement());
                }
            }

            return null;
        });
    }

    @Override
    public ClassElement getSuperClassElement() {
        return superClassElement.get();
    }

    @Override
    public Collection<FieldElement> getFieldElements() {
        return null;
    }

    @Override
    public Collection<MethodElement> getMethodElements() {
        return null;
    }

}
