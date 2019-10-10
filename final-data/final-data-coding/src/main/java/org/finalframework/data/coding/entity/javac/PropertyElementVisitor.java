package org.finalframework.data.coding.entity.javac;

import com.sun.tools.javac.code.Type;
import org.finalframework.data.coding.entity.BaseProperty;
import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.tools.Diagnostic;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-09 15:25:34
 * @since 1.0
 */
public class PropertyElementVisitor extends SimpleElementVisitor8<Property, Entity<Property>> {

    private final ProcessingEnvironment processEnv;
    private final Messager messager;

    public PropertyElementVisitor(ProcessingEnvironment processEnv) {
        this.processEnv = processEnv;
        this.messager = processEnv.getMessager();
    }

    @Override
    public Property visitVariable(VariableElement e, Entity entity) {
        info("visitVariable start: %s", e.getSimpleName());
        info("kind：%s", e.getKind().name());
        TypeMirror typeMirror = e.asType();
        info("typeClass:%s", typeMirror.getClass());
        if (typeMirror instanceof Type.AnnotatedType) {
            info("type:%s", ((Type.AnnotatedType) typeMirror).unannotatedType());
        } else {
            info("type:%s", typeMirror.toString());
        }
        info("visitVariable end:%s", e.getSimpleName());
        return buildProperty(e, entity);

    }

    @Override
    public Property visitExecutable(ExecutableElement e, Entity entity) {
        return buildProperty(e, entity);
    }

    @SuppressWarnings("all")
    private Property buildProperty(Element e, Entity entity) {
        return new BaseProperty(entity, processEnv, e);
    }

    private void info(String message, Object... args) {
        log(Diagnostic.Kind.NOTE, message, args);
    }

    private void error(String message, Object... args) {
        log(Diagnostic.Kind.ERROR, message, args);
    }

    private void log(Diagnostic.Kind kind, String message, Object... args) {
        messager.printMessage(kind, String.format(message, args));
    }

}
