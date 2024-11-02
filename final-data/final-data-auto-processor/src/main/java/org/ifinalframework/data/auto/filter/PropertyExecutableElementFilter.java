/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.auto.filter;

import org.ifinalframework.util.Asserts;
import org.ifinalframework.util.function.Filter;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <h3>过滤不支持的{@link ExecutableElement}</h3>
 * <ol>
 *     <li>非 {@link ElementKind#FIELD}类型的</li>
 *     <li>被{@link Modifier#STATIC}或{@link Modifier#FINAL}修饰的</li>
 * </ol>
 * 仅 {@link ElementKind#FIELD}
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class PropertyExecutableElementFilter implements Filter<ExecutableElement> {

    private static final Set<String> GETTER_PREFIX = new HashSet<>(Arrays.asList("is", "get"));

    /**
     * @see Modifier#FINAL
     * @see Modifier#STATIC
     * @see Modifier#TRANSIENT
     * @see Modifier#ABSTRACT
     */
    private Set<Modifier> ignoreModifiers = new HashSet<>();

    public PropertyExecutableElementFilter() {
        ignoreModifiers.addAll(Arrays.asList(Modifier.ABSTRACT, Modifier.STATIC, Modifier.FINAL, Modifier.TRANSIENT));
    }

    @Override
    public boolean matches(final ExecutableElement e) {

        if (ElementKind.METHOD != e.getKind()) {
            return false;
        }
        Set<Modifier> modifiers = e.getModifiers();
        for (Modifier modifier : ignoreModifiers) {
            if (modifiers.contains(modifier)) {
                return false;
            }
        }

        for (String prefix : GETTER_PREFIX) {
            if (e.getSimpleName().toString().startsWith(prefix)) {
                return true;
            }
        }

        return true;
    }

    public void setIgnoreModifiers(final Set<Modifier> ignoreModifiers) {

        this.ignoreModifiers.clear();
        if (Asserts.nonEmpty(ignoreModifiers)) {
            this.ignoreModifiers.addAll(ignoreModifiers);
        }
    }

}

