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


import javax.lang.model.element.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 21:13:42
 * @since 1.0
 */
public interface IElementVisitor<R, P> extends ElementVisitor<R, P> {
    @Override
    default R visit(Element e, P p) {
        return null;
    }

    @Override
    default R visit(Element e) {
        return null;
    }

    @Override
    default R visitPackage(PackageElement e, P p) {
        return null;
    }

    @Override
    default R visitType(TypeElement e, P p) {
        return null;
    }

    @Override
    default R visitVariable(VariableElement e, P p) {
        return null;
    }

    @Override
    default R visitExecutable(ExecutableElement e, P p) {
        return null;
    }

    @Override
    default R visitTypeParameter(TypeParameterElement e, P p) {
        return null;
    }

    @Override
    default R visitUnknown(Element e, P p) {
        return null;
    }
}

