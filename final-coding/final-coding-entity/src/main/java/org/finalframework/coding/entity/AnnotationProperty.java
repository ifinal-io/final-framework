package org.finalframework.coding.entity;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleTypeVisitor8;
import javax.lang.model.util.Types;

import org.finalframework.coding.beans.PropertyDescriptor;
import org.finalframework.coding.utils.TypeElements;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.*;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.PrimaryKeyType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.springframework.data.util.Lazy;
import org.springframework.data.util.Optionals;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 10:18
 * @since 1.0
 */
public class AnnotationProperty implements Property {
    private static final Set<String> GETTER_PREFIX = new HashSet<>(Arrays.asList("is", "get"));
    /**
     * 虚拟列前缀
     */
    private static final String VIRTUAL_PREFIX = "v_";
    private final ProcessingEnvironment processEnv;
    private final Elements elements;
    private final Types types;
    private final TypeElements typeElements;
    private final Lazy<Element> element;
    private final List<TypeElement> views = new ArrayList<>();
    private TypeElement javaTypeElement;
    private TypeElement metaTypeElement;
    private final Optional<VariableElement> field;
    private final Optional<PropertyDescriptor> descriptor;
    private final Optional<ExecutableElement> readMethod;
    private final Optional<ExecutableElement> writeMethod;
    private final Lazy<String> name;
    private final Lazy<String> column;
    private final Lazy<TypeMirror> type;
    private PersistentType persistentType = PersistentType.AUTO;
    private boolean unique = false;
    private boolean nonnull = true;
    private boolean updatable = true;
    private final String componentType = null;
    private final String mapKeyType = null;
    private final String mapValueType = null;
    private final Lazy<Boolean> isIdProperty;
    private final Lazy<Boolean> isReference;
    private final Lazy<Boolean> isVersion;

    private final Lazy<Boolean> isDefault;
    private final Lazy<Boolean> isVirtual;
    private final Lazy<Boolean> isWritable;
    private final Lazy<Boolean> isReadable;
    private final Lazy<Boolean> isTransient;
    private final Lazy<Boolean> isPrimitive;
    private final Lazy<Boolean> isEnum;
    private final Lazy<Boolean> isArray;
    private final Lazy<Boolean> isCollection;
    private final Lazy<Boolean> isList;
    private final Lazy<Boolean> isSet;
    private final Lazy<Boolean> isMap;
    private PrimaryKeyType primaryKeyType = PrimaryKeyType.AUTO_INC;
    private boolean placeholder = true;
    private List<String> referenceProperties;
    private ReferenceMode referenceMode;
    private Map<String, String> referenceColumns;

    public AnnotationProperty(ProcessingEnvironment processEnv, Optional<VariableElement> field, Optional<PropertyDescriptor> descriptor) {
        this.processEnv = processEnv;
        this.elements = processEnv.getElementUtils();
        this.types = processEnv.getTypeUtils();
        this.typeElements = new TypeElements(processEnv.getTypeUtils(), processEnv.getElementUtils());
        this.field = field;
        this.descriptor = descriptor;

        if (descriptor.isPresent()) {
            this.readMethod = descriptor.get().getGetter();
            this.writeMethod = descriptor.get().getSetter();
        } else {
            this.readMethod = Optional.empty();
            this.writeMethod = Optional.empty();
        }

        this.element = Lazy.of(() -> withFieldOrMethod(Function.identity(), Function.identity(), Function.identity()));
        this.name = Lazy.of(() -> withFieldOrDescriptor(it -> it.getSimpleName().toString(), PropertyDescriptor::getName));

        this.type = Lazy.of(() ->
                withFieldOrMethod(
                        Element::asType,
                        setter -> setter.getParameters().get(0).asType(),
                        ExecutableElement::getReturnType
                )
        );

        this.isPrimitive = Lazy.of(getType() instanceof PrimitiveType);
        this.isEnum = Lazy.of(() -> isEnum(getType()));
        this.isArray = Lazy.of(getType() instanceof ArrayType);
        this.isCollection = Lazy.of(() -> isCollection(getType()));
        this.isList = Lazy.of(() -> isList(getType()));
        this.isSet = Lazy.of(() -> isSet(getType()));
        this.isMap = Lazy.of(() -> isMap(getType()));

        this.isIdProperty = Lazy.of(!isTransient() && hasAnnotation(PrimaryKey.class));
        this.isVirtual = Lazy.of(!isTransient() && hasAnnotation(Virtual.class));
        this.isReference = Lazy.of(!isTransient() && hasAnnotation(Reference.class));
        this.isVersion = Lazy.of(!isTransient() && hasAnnotation(Version.class));
        this.isDefault = Lazy.of(!isTransient() && hasAnnotation(Default.class));
        this.isWritable = Lazy.of(() -> !isTransient() && !hasAnnotation(ReadOnly.class));
        this.isReadable = Lazy.of(() -> !isTransient() && !hasAnnotation(WriteOnly.class));
        this.isTransient = Lazy.of(() -> hasAnnotation(Transient.class));

        PropertyJavaTypeVisitor propertyJavaTypeVisitor = new PropertyJavaTypeVisitor(processEnv);
        this.javaTypeElement = getType().accept(propertyJavaTypeVisitor, this);


        if (isReference()) {
            initReferenceColumn(getAnnotation(Reference.class));
        }
        initColumnView();

        this.column = Lazy.of(() -> isVirtual() ? VIRTUAL_PREFIX + initColumn() : initColumn());

        System.out.println("=================================================" + getName() + "=================================================");
        System.out.println("name：" + getName());
        System.out.println("column：" + getColumn());
        System.out.println("javaType：" + getJavaTypeElement().getQualifiedName().toString());
        System.out.println("isPrimitive：" + isPrimitive());
        System.out.println("isEnum：" + isEnum());
        System.out.println("isArray：" + isArray());
        System.out.println("isCollection：" + isCollection());
        System.out.println("isList：" + isList());
        System.out.println("isSet：" + isSet());
        System.out.println("isMap：" + isMap());
        System.out.println("isCollection：" + isCollection());
        System.out.println("isIdProperty：" + isIdProperty());
        System.out.println("isVersion：" + isVersion());
        System.out.println("isReference：" + isReference());
        System.out.println("===================================================================================================================");


    }

    private String initColumn() {
        List<? extends AnnotationMirror> annotationMirrors = getElement().getAnnotationMirrors();
        TypeElement column = typeElements.getTypeElement(Column.class);
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            if (typeElements.isSameType(annotationType, column.asType())) {
                return getColumnValue(annotationMirror);
            } else if (annotationType.getAnnotation(Column.class) != null) {
                return getColumnValue(annotationMirror);
            }
        }

        return getName();
    }

    private String getColumnValue(AnnotationMirror annotation) {
        DeclaredType annotationType = annotation.getAnnotationType();
        Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotation.getElementValues();
        String column = "";
        String value = "";
        System.out.println(annotationType.toString() + "sdfasdfajhwehfas===================");
        for (ExecutableElement method : elementValues.keySet()) {
            System.out.println(annotationType.toString() + ":" + method.getSimpleName().toString() + ":" + elementValues.get(method));
            if ("name".equals(method.getSimpleName().toString())) {
                column = (String) elementValues.get(method).getValue();
            } else if ("value".equals(method.getSimpleName().toString())) {
                value = (String) elementValues.get(method).getValue();
            }
        }

        return Stream.of(value, column).filter(Assert::nonBlank).findFirst().orElse(getName());
    }


    private void initReferenceColumn(Reference ann) {
        initReference(ann.mode(), ann.properties(), ann.delimiter());
    }

    private void initColumnView() {
        List<? extends AnnotationMirror> annotationMirrors = getElement().getAnnotationMirrors();
        TypeElement columnView = typeElements.getTypeElement(View.class);
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            if (typeElements.isSameType(annotationType, columnView.asType())) {
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


    private void initReference(ReferenceMode mode, String[] properties, String delimiter) {
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
        return this.element.get();
    }

    @Override
    public VariableElement getField() {
        return field.orElse(null);
    }

    @Override
    public ExecutableElement getWriteMethod() {
        return writeMethod.orElse(null);
    }

    @Override
    public ExecutableElement getReadMethod() {
        return readMethod.orElse(null);
    }

    @Override
    public String getName() {
        return name.get();
    }

    @Override
    public String getColumn() {
        return column.get();
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
    public boolean isDefault() {
        return isDefault.get();
    }

    @Override
    public boolean isVirtual() {
        return isVirtual.get();
    }

    @Override
    public boolean isWriteOnly() {
        return isWritable.get();
    }

    @Override
    public boolean updatable() {
        return updatable;
    }

    @Override
    public boolean isReadOnly() {
        return isReadable.get();
    }

    @Override
    public boolean placeholder() {
        return placeholder;
    }

    @Override
    public TypeMirror getType() {
        return type.get();
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
        return isIdProperty.get();
    }

    @Override
    public boolean isVersion() {
        return isVersion.get();
    }

    @Override
    public PrimaryKeyType getPrimaryKeyType() {
        return primaryKeyType;
    }

    @Override
    public boolean isPrimitive() {
        return isPrimitive.get();
    }

    @Override
    public boolean isEnum() {
        return isEnum.get();
    }

    @Override
    public boolean isCollection() {
        return isCollection.get();
    }

    @Override
    public boolean isList() {
        return isList.get();
    }

    @Override
    public boolean isSet() {
        return isSet.get();
    }

    @Override
    public boolean isMap() {
        return isMap.get();
    }

    @Override
    public boolean isArray() {
        return isArray.get();
    }

    @Override
    public boolean isReference() {
        return isReference.get();
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
        return getElement().getAnnotation(annotationType);
    }

    @Override
    public Entity toEntity() {
        return EntityFactory.create(processEnv, getJavaTypeElement());
    }

    @Override
    public boolean hasView(TypeElement view) {

        for (TypeElement typeElement : views) {
            if (typeElements.isAssignable(view.asType(), typeElement.asType())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isTransient() {
        return isTransient != null && Boolean.TRUE.equals(isTransient.get());
    }

    private <T> T withFieldOrDescriptor(Function<? super VariableElement, T> field,
                                        Function<? super PropertyDescriptor, T> descriptor) {

        return Optionals.firstNonEmpty(//
                () -> this.field.map(field), //
                () -> this.descriptor.map(descriptor))//
                .orElseThrow(() -> new IllegalStateException("Should not occur! Either field or descriptor has to be given"));
    }

    private <T> T withFieldOrMethod(Function<? super VariableElement, T> field,
                                    Function<? super ExecutableElement, T> setter,
                                    Function<? super ExecutableElement, T> getter
    ) {

        return Optionals.firstNonEmpty(//
                () -> this.field.map(field), //
                () -> this.writeMethod.map(setter),
                () -> this.readMethod.map(getter))//
                .orElseThrow(() -> new IllegalStateException("Should not occur! Either field or descriptor has to be given"));
    }

    private boolean isEnum(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(IEnum.class).asType());
    }

    private boolean isCollection(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(Collection.class).asType());
    }

    private boolean isList(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(List.class).asType());
    }

    private boolean isSet(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(Set.class).asType());
    }

    private boolean isMap(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(Map.class).asType());
    }


    private TypeElement getPrimitiveTypeElement(TypeKind kind) {
        switch (kind) {
            case BOOLEAN:
                return getTypeElement(Boolean.class);
            case BYTE:
                return getTypeElement(Byte.class);
            case SHORT:
                return getTypeElement(Short.class);
            case INT:
                return getTypeElement(Integer.class);
            case LONG:
                return getTypeElement(Long.class);
            case CHAR:
                return getTypeElement(Character.class);
            case FLOAT:
                return getTypeElement(Float.class);
            case DOUBLE:
                return getTypeElement(Double.class);
        }

        throw new IllegalArgumentException("不支持的类型：" + kind);
    }


    private TypeElement getTypeElement(Class<?> type) {
        return elements.getTypeElement(type.getCanonicalName());
    }


    private static final class PropertyJavaTypeVisitor extends SimpleTypeVisitor8<TypeElement, AnnotationProperty> {
        @NonNull
        private final ProcessingEnvironment pv;
        @NonNull
        private final Elements elements;
        @NonNull
        private final Types types;

        public PropertyJavaTypeVisitor(@NonNull ProcessingEnvironment pv) {
            this.pv = pv;
            this.elements = pv.getElementUtils();
            this.types = pv.getTypeUtils();
        }


        /**
         * Represents a primitive type.  These include
         * {@code boolean}, {@code byte}, {@code short}, {@code int},
         * {@code long}, {@code char}, {@code float}, and {@code double}.
         *
         * @return
         */
        @Override
        public TypeElement visitPrimitive(PrimitiveType type, AnnotationProperty property) {
            return property.getPrimitiveTypeElement(type.getKind());
        }

        @Override
        public TypeElement visitArray(ArrayType array, AnnotationProperty property) {
            TypeMirror type = array.getComponentType();
            TypeKind kind = type.getKind();
            if (kind.isPrimitive()) {
                return property.getPrimitiveTypeElement(kind);
            }

            if (type instanceof ArrayType || property.isCollection(type)) {
                throw new IllegalArgumentException("不支持的Array类型：[]" + type.toString());
            }

            if (type instanceof DeclaredType) {
                DeclaredType declaredType = (DeclaredType) type;
                Element element = declaredType.asElement();
                if (element instanceof TypeElement) {
                    return (TypeElement) element;
                }
            }


            throw new IllegalArgumentException("不支持的Array类型：[]" + type.toString());
        }

        @Override
        public TypeElement visitDeclared(DeclaredType type, AnnotationProperty property) {
            if (property.isMap()) {
                return property.getTypeElement(Map.class);
            }

            if (property.isCollection()) {
                TypeMirror elementType = type.getTypeArguments().get(0);
                TypeKind kind = elementType.getKind();
                if (kind.isPrimitive()) return property.getPrimitiveTypeElement(kind);
                if (TypeKind.DECLARED == kind && !property.isCollection(elementType)) {
                    DeclaredType declaredType = (DeclaredType) elementType;
                    Element element = declaredType.asElement();
                    if (element instanceof TypeElement) {
                        return (TypeElement) element;
                    }
                }

            }

            Element element = type.asElement();
            if (element instanceof TypeElement) {
                return (TypeElement) element;
            }

            throw new IllegalArgumentException("不支持的Array类型：[]" + type.toString());
        }

    }
}
