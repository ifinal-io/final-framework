package org.finalframework.coding.entity.filter;


import org.finalframework.core.Assert;
import org.finalframework.core.filter.Filter;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <h4>过滤不支持的{@link VariableElement}</h4>
 * <ol>
 *     <li>非 {@link ElementKind#FIELD}类型的</li>
 *     <li>被{@link Modifier#STATIC}或{@link Modifier#FINAL}修饰的</li>
 * </ol>
 * 仅 {@link ElementKind#FIELD}
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-14 21:19:22
 * @since 1.0
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
        if (Assert.nonEmpty(ignoreModifiers)) {
            this.ignoreModifiers.addAll(ignoreModifiers);
        }
    }

}

