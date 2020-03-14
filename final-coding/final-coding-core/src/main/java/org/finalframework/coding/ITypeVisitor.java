package org.finalframework.coding;

import javax.lang.model.type.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 21:43:16
 * @since 1.0
 */
public class ITypeVisitor<R, P> implements TypeVisitor<P, R> {
    @Override
    public P visit(TypeMirror t, R r) {
        return null;
    }

    @Override
    public P visit(TypeMirror t) {
        return null;
    }

    @Override
    public P visitPrimitive(PrimitiveType t, R r) {
        return null;
    }

    @Override
    public P visitNull(NullType t, R r) {
        return null;
    }

    @Override
    public P visitArray(ArrayType t, R r) {
        return null;
    }

    @Override
    public P visitDeclared(DeclaredType t, R r) {
        return null;
    }

    @Override
    public P visitError(ErrorType t, R r) {
        return null;
    }

    @Override
    public P visitTypeVariable(TypeVariable t, R r) {
        return null;
    }

    @Override
    public P visitWildcard(WildcardType t, R r) {
        return null;
    }

    @Override
    public P visitExecutable(ExecutableType t, R r) {
        return null;
    }

    @Override
    public P visitNoType(NoType t, R r) {
        return null;
    }

    @Override
    public P visitUnknown(TypeMirror t, R r) {
        return null;
    }

    @Override
    public P visitUnion(UnionType t, R r) {
        return null;
    }

    @Override
    public P visitIntersection(IntersectionType t, R r) {
        return null;
    }
}
