package org.finalframework.coding.entity;

import org.finalframework.core.Assert;
import org.finalframework.data.annotation.Schema;
import org.finalframework.data.annotation.Table;
import org.finalframework.data.mapping.converter.NameConverterRegistry;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 09:57
 * @since 1.0
 */
public class BaseEntity implements MutableEntity {

    private final ProcessingEnvironment processEnv;
    private final Elements elements;
    private final Types types;
    private final TypeElement typeElement;
    private final String packageName;
    private final String simpleName;
    private final String name;
    private final String schema;
    private final String table;
    private final String type;
    private final List<Property> properties = new LinkedList<>();
    private final Map<String, Property> propertyCache = new LinkedHashMap<>();
    private final Set<TypeElement> views = new HashSet<>();
    private Property idProperty;
    private Property versionProperty;

    public BaseEntity(ProcessingEnvironment processEnv, TypeElement typeElement) {
        this.processEnv = processEnv;
        this.elements = processEnv.getElementUtils();
        this.types = processEnv.getTypeUtils();
        this.typeElement = typeElement;
        this.packageName = elements.getPackageOf(typeElement).toString();
        this.name = typeElement.getQualifiedName().toString();
        this.schema = Optional.ofNullable(typeElement.getAnnotation(Schema.class)).map(Schema::value).orElse(null);
        this.table = NameConverterRegistry.getInstance().getTableNameConverter().convert(initTable());
        this.simpleName = typeElement.getSimpleName().toString();
        this.type = types.erasure(typeElement.asType()).toString();

    }

    private String initTable() {
        Table table = getAnnotation(Table.class);
        return Assert.isNull(table) ? this.typeElement.getSimpleName().toString() : table.value();
    }

    @Override
    public void addProperty(Property property) {

        if (property.isIdProperty()) {
            if (idProperty != null) {
                throw new IllegalArgumentException("the entity must only have only one id property!,entity=" + getType());
            }
            this.idProperty = property;
        } else if (property.isVersion()) {
            this.versionProperty = property;
        }


        if (propertyCache.containsKey(property.getName())) {
            throw new IllegalArgumentException(String.format("the entity have not only one property named %s", property.getName()));
        } else {
            propertyCache.put(property.getName(), property);
            properties.add(property);
        }

        List<TypeElement> views = property.getViews();
        if (Assert.nonEmpty(views)) {
            this.views.addAll(views);
        }

    }

    @Override
    public String getPackage() {
        return packageName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public TypeElement getElement() {
        return typeElement;
    }

    @Override
    public String getRawType() {
        return typeElement.toString();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public Property getProperty(String name) {
        return propertyCache.get(name);
    }

    @Override
    public Property getIdProperty() {
        return idProperty;
    }

    @Override
    public Property getVersionProperty() {
        return versionProperty;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return typeElement.getAnnotation(annotationType);
    }

    @Override
    public Collection<TypeElement> getViews() {
        return views;
    }

    @Override
    public Stream<Property> stream() {
        return properties.stream();
    }

    @Override
    public Iterator<Property> iterator() {
        return properties.iterator();
    }
}
