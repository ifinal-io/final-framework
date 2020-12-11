package org.ifinal.finalframework.auto.coding;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.UnionType;
import javax.lang.model.type.WildcardType;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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
