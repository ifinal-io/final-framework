package org.ifinal.finalframework.auto.model;

import org.springframework.lang.NonNull;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.AbstractAnnotationValueVisitor8;
import java.util.List;

/**
 * Annotation Values Utils.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class AnnotationValues {

    private static final BooleanAnnotationVisitor BOOLEAN_ANNOTATION_VISITOR = new BooleanAnnotationVisitor();

    private AnnotationValues() {
    }

    /**
     * return {@code boolean} value from the {@link AnnotationValue}.
     *
     * @param value annotation value
     * @return {@code boolean} value
     */
    public static boolean getBoolean(final @NonNull AnnotationValue value) {
        return value.accept(BOOLEAN_ANNOTATION_VISITOR, null);
    }

    /**
     * return {@link Class} value from the {@link AnnotationValue}.
     *
     * @param value annotation value
     * @return {@link Class} value
     */
    public static TypeElement getClass(final @NonNull AnnotationValue value) {
        return (TypeElement) ((DeclaredType) value.getValue()).asElement();
    }

    protected abstract static class AbsAnnotationValueVisitor<R, P> extends
            AbstractAnnotationValueVisitor8<R, P> {

        @Override
        public R visitBoolean(final boolean b, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitByte(final byte b, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitChar(final char c, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitDouble(final double d, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitFloat(final float f, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitInt(final int i, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitLong(final long i, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitShort(final short s, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitString(final String s, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitType(final TypeMirror t, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitEnumConstant(final VariableElement c, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitAnnotation(final AnnotationMirror a, final P p) {
            throw new IllegalArgumentException("");
        }

        @Override
        public R visitArray(final List<? extends AnnotationValue> vals, final P p) {
            throw new IllegalArgumentException("");
        }

    }

    private static class BooleanAnnotationVisitor extends AbsAnnotationValueVisitor<Boolean, Void> {

        @Override
        public Boolean visitBoolean(final boolean av, final Void unused) {
            return av;
        }

    }

}
