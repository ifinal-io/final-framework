package org.ifinal.finalframework.auto.data.filter;


import org.ifinal.finalframework.util.Asserts;
import org.ifinal.finalframework.util.function.Filter;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <h3>过滤不支持的{@link VariableElement}</h3>
 * <ol>
 *     <li>非 {@link ElementKind#FIELD}类型的</li>
 *     <li>被{@link Modifier#STATIC}或{@link Modifier#FINAL}修饰的</li>
 * </ol>
 * 仅 {@link ElementKind#FIELD}
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class PropertyVariableElementFilter implements Filter<VariableElement> {

    /**
     * @see Modifier#FINAL
     * @see Modifier#STATIC
     * @see Modifier#TRANSIENT
     * @see Modifier#ABSTRACT
     */
    private Set<Modifier> ignoreModifiers = new HashSet<>();

    public PropertyVariableElementFilter() {
        ignoreModifiers.addAll(Arrays.asList(Modifier.ABSTRACT, Modifier.STATIC, Modifier.FINAL, Modifier.TRANSIENT));
    }

    @Override
    public boolean matches(VariableElement e) {
        if (ElementKind.FIELD != e.getKind()) return false;
        Set<Modifier> modifiers = e.getModifiers();
        for (Modifier modifier : ignoreModifiers) {
            if (modifiers.contains(modifier)) return false;
        }
        return true;
    }

    public void setIgnoreModifiers(Set<Modifier> ignoreModifiers) {
        this.ignoreModifiers.clear();
        if (Asserts.nonEmpty(ignoreModifiers)) {
            this.ignoreModifiers.addAll(ignoreModifiers);
        }
    }

}

