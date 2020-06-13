/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
