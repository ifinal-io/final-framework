package org.finalframework.coding.entity;

import com.sun.tools.javac.code.Type;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.*;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.PrimaryKeyType;
import org.finalframework.data.annotation.enums.ReferenceMode;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 10:18
 * @since 1.0
 */
public class BaseProperty<T extends Entity, P extends Property<T, P>> implements Property<T, P> {

    private static final Set<String> GETTER_PREFIX = new HashSet<>(Arrays.asList("is", "get"));

    private final T entity;
    private final ProcessingEnvironment processEnv;
    private final Elements elements;
    private final Types types;
    private final Element element;
    private PrimaryKeyType primaryKeyType;
    private final List<TypeElement> views = new ArrayList<>();

    private final String name;
    private TypeElement javaTypeElement;
    private TypeElement metaTypeElement;
    private String table;
    private String column;
    private PersistentType persistentType = PersistentType.AUTO;
    private boolean unique = false;
    private boolean nonnull = true;
    private boolean insertable = true;
    private boolean updatable = true;
    private final String type;
    private final String componentType;
    private final String mapKeyType;
    private final String mapValueType;
    private final boolean isCollection;
    private final boolean isMap;
    private boolean isIdProperty;
    private boolean isVersionProperty;
    private boolean selectable = true;
    private boolean isReference;
    private boolean placeholder = true;
    private List<String> referenceProperties;
    private ReferenceMode referenceMode;
    private Map<String, String> referenceColumns;

    public BaseProperty(T entity, ProcessingEnvironment processEnv, Element element) {
        this.entity = entity;
        this.processEnv = processEnv;
        this.elements = processEnv.getElementUtils();
        this.types = processEnv.getTypeUtils();
        this.element = element;
        this.name = getElementName(element);
        TypeMirror typeMirror = element.asType();
        TypeMirror realTypeMirror = getRealTypeMirror(typeMirror);
        this.javaTypeElement = (TypeElement) ((Type) typeMirror).asElement();
        this.metaTypeElement = (TypeElement) ((Type) typeMirror).asElement();

        final TypeKind kind = typeMirror.getKind();
        if (kind.isPrimitive()) {
            switch (kind) {
                case INT:
                    this.metaTypeElement = elements.getTypeElement(Integer.class.getCanonicalName());
                    break;
                case BYTE:
                    this.metaTypeElement = elements.getTypeElement(Byte.class.getCanonicalName());
                    break;
                case CHAR:
                    this.metaTypeElement = elements.getTypeElement(Character.class.getCanonicalName());
                    break;
                case LONG:
                    this.metaTypeElement = elements.getTypeElement(Long.class.getCanonicalName());
                    break;
                case FLOAT:
                    this.metaTypeElement = elements.getTypeElement(Float.class.getCanonicalName());
                    break;
                case DOUBLE:
                    this.metaTypeElement = elements.getTypeElement(Double.class.getCanonicalName());
                    break;
                case BOOLEAN:
                    this.metaTypeElement = elements.getTypeElement(Boolean.class.getCanonicalName());
                    break;
                case SHORT:
                    this.metaTypeElement = elements.getTypeElement(Short.class.getCanonicalName());
                    break;
                default:
                    throw new IllegalArgumentException("不支持的基础类型");
            }
        }

        this.type = types.erasure(typeMirror).toString();

        if (element.getKind().isField()) {
            this.isCollection = types.isAssignable(types.erasure(typeMirror), elements
                    .getTypeElement("java.util.Collection")
                    .asType());
            if (isCollection) {
                TypeMirror metaType = ((DeclaredType) typeMirror).getTypeArguments().get(0);
                this.metaTypeElement = (TypeElement) ((DeclaredType) metaType).asElement();
                componentType = metaType.toString();
            } else {
                componentType = null;
            }

            this.isMap = types.isAssignable(types.erasure(typeMirror), elements
                    .getTypeElement("java.util.Map")
                    .asType());
            if (isMap) {
                List<? extends TypeMirror> arguments = ((DeclaredType) typeMirror).getTypeArguments();
                this.mapKeyType = arguments.get(0).toString();
                this.mapValueType = arguments.get(1).toString();
                this.metaTypeElement = elements.getTypeElement(Map.class.getCanonicalName());
            } else {
                mapKeyType = null;
                mapValueType = null;
            }
        } else {
            this.componentType = null;
            this.mapKeyType = null;
            this.mapValueType = null;
            this.isCollection = false;
            this.isMap = false;
        }

        initColumn();
        initColumnView();

        if (hasAnnotation(ReadOnly.class)) {
            this.insertable = false;
            this.updatable = false;
        }

        if (Assert.isBlank(this.column)) {
            this.column = this.name;
        }

    }

    private void initColumn() {
        if (hasAnnotation(Column.class)) {
            initColumn(getAnnotation(Column.class));
        } else if (hasAnnotation(JsonColumn.class)) {
            initJsonColumn(getAnnotation(JsonColumn.class));
        } else if (hasAnnotation(Created.class)) {
            initCreated(getAnnotation(Created.class));
        } else if (hasAnnotation(LastModified.class)) {
            initLastModified(getAnnotation(LastModified.class));
        } else if (hasAnnotation(Version.class)) {
            initVersion(getAnnotation(Version.class));
        } else if (hasAnnotation(ReferenceColumn.class)) {
            initReferenceColumn(getAnnotation(ReferenceColumn.class));
        } else if (hasAnnotation(PrimaryKey.class)) {
            initPrimaryKey(getAnnotation(PrimaryKey.class));
        }
    }

    private void initColumn(Column ann) {
        this.placeholder = ann.placeholder();
        this.persistentType = ann.persistentType();
        this.unique = ann.unique();
        this.nonnull = ann.nonnull();
        this.insertable = ann.insertable();
        this.updatable = ann.updatable();
        this.selectable = ann.selectable();
        this.table = ann.table();
        this.column = ann.value();
    }

    private void initJsonColumn(JsonColumn ann) {
        this.placeholder = ann.placeholder();
        this.persistentType = ann.persistentType();
        this.unique = ann.unique();
        this.nonnull = ann.nonnull();
        this.insertable = ann.insertable();
        this.updatable = ann.updatable();
        this.selectable = ann.selectable();
        this.table = ann.table();
        this.column = ann.value();
    }

    private void initCreated(Created ann) {
        this.placeholder = ann.placeholder();
        this.persistentType = ann.persistentType();
        this.unique = ann.unique();
        this.nonnull = ann.nonnull();
        this.insertable = ann.insertable();
        this.updatable = ann.updatable();
        this.selectable = ann.selectable();
        this.table = ann.table();
        this.column = ann.value();
    }

    private void initLastModified(LastModified ann) {
        this.placeholder = ann.placeholder();
        this.persistentType = ann.persistentType();
        this.unique = ann.unique();
        this.nonnull = ann.nonnull();
        this.insertable = ann.insertable();
        this.updatable = ann.updatable();
        this.selectable = ann.selectable();
        this.table = ann.table();
        this.column = ann.value();
    }

    private void initVersion(Version ann) {
        this.placeholder = ann.placeholder();
        this.persistentType = ann.persistentType();
        this.unique = ann.unique();
        this.nonnull = ann.nonnull();
        this.insertable = ann.insertable();
        this.updatable = ann.updatable();
        this.selectable = ann.selectable();
        this.table = ann.table();
        this.column = ann.value();
        this.isVersionProperty = true;
    }

    private void initReferenceColumn(ReferenceColumn ann) {
        initReference(ann.mode(), ann.properties(), ann.delimiter());
    }

    private void initPrimaryKey(PrimaryKey ann) {
        this.isIdProperty = true;
        this.primaryKeyType = ann.type();
        this.unique = ann.unique();
        this.nonnull = ann.nonnull();
        this.insertable = ann.insertable();
        this.updatable = ann.updatable();
        this.selectable = ann.selectable();
    }

    private void initColumnView() {
        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        TypeElement columnView = elements.getTypeElement(ColumnView.class.getCanonicalName());
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            if (types.isSameType(annotationMirror.getAnnotationType(), columnView.asType())) {
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
                columnView.getEnclosedElements().stream()
                        .map(it -> (ExecutableElement) it)
                        .forEach(method -> {
                            List<AnnotationValue> views = (List<AnnotationValue>) elementValues.get(method).getValue();
                            views.stream().map(AnnotationValue::getValue).map(it -> (TypeElement) ((DeclaredType) it).asElement()).forEach(this.views::add);
                        });

            }
        }
    }

    private TypeMirror getRealTypeMirror(TypeMirror typeMirror) {
        if (typeMirror instanceof Type.AnnotatedType) {
            return ((Type.AnnotatedType) typeMirror).unannotatedType();
        }

        return typeMirror;
    }

    private void initReference(ReferenceMode mode, String[] properties, String delimiter) {
        this.isReference = true;
        this.referenceMode = mode;
        List<String> referenceProperties = new ArrayList<>(properties.length);
        Map<String, String> referenceColumns = new HashMap<>(properties.length);
        for (String property : properties) {
            if (property.contains(delimiter)) {
                final String[] split = property.split(delimiter);
                referenceProperties.add(split[0]);
                referenceColumns.put(split[0], split[1]);
            } else {
                referenceProperties.add(property);
            }
        }
        this.referenceProperties = referenceProperties;
        this.referenceColumns = referenceColumns;
    }

    private String getElementName(Element element) {
        if (element.getKind().isField()) {
            return element.getSimpleName().toString();
        }

        final String elementName = element.getSimpleName().toString();

        for (String prefix : GETTER_PREFIX) {
            if (elementName.startsWith(prefix)) {
                String name = elementName.substring(prefix.length());
                return name.substring(0, 1).toLowerCase() + name.substring(1);
            }
        }
        return elementName;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColumn() {
        return column;
    }

    @Override
    public PersistentType getPersistentType() {
        return persistentType;
    }

    @Override
    public List<TypeElement> getViews() {
        return views;
    }

    @Override
    public boolean hasView(Class<?> view) {
        return false;
    }

    @Override
    public boolean unique() {
        return unique;
    }

    @Override
    public boolean nonnull() {
        return nonnull;
    }

    @Override
    public boolean insertable() {
        return insertable;
    }

    @Override
    public boolean updatable() {
        return updatable;
    }

    @Override
    public boolean selectable() {
        return selectable;
    }

    @Override
    public boolean placeholder() {
        return placeholder;
    }

    @Override
    public String getType() {
        return type;
    }


    @Override
    public TypeElement getJavaTypeElement() {
        return javaTypeElement;
    }

    @Override
    public TypeElement getMetaTypeElement() {
        return metaTypeElement;
    }

    @Override
    public String getMapKeyType() {
        return mapKeyType;
    }

    @Override
    public String getMapValueType() {
        return mapValueType;
    }

    @Override
    public boolean isIdProperty() {
        return isIdProperty;
    }

    @Override
    public boolean isVersionProperty() {
        return isVersionProperty;
    }

    @Override
    public PrimaryKeyType getPrimaryKeyType() {
        return primaryKeyType;
    }

    @Override
    public boolean isCollection() {
        return isCollection;
    }

    @Override
    public boolean isMap() {
        return isMap;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isReference() {
        return isReference;
    }

    @Override
    public ReferenceMode referenceMode() {
        return referenceMode;
    }

    @Override
    public List<String> referenceProperties() {
        return referenceProperties;
    }

    @Override
    public String referenceColumn(String property) {
        return referenceColumns == null ? null : referenceColumns.get(property);
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return element.getAnnotation(annotationType);
    }

    @Override
    public Entity toEntity() {
        return EntityFactory.create(processEnv, processEnv.getElementUtils().getTypeElement(element.asType().toString()));
    }

    @Override
    public boolean hasView(TypeElement view) {

        for (TypeElement typeElement : views) {
            if (types.isAssignable(types.erasure(view.asType()), types.erasure(typeElement.asType()))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isTransient() {
        return hasAnnotation(Transient.class);
    }
}
