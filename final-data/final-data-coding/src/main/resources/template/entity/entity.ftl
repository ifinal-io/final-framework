package ${package};

import ${entity.package}.${entity.simpleName};
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.QEntity;

import javax.annotation.Generated;

@Generated("org.finalframework.coding.EntityProcessor")
public final class ${name}{

    private ${name}(){}

public static final QEntity<${entity.idProperty.rawType},${entity.simpleName}> ${entity.simpleName} = QEntity.from(${entity.simpleName}.class);
<#list properties as property>
    public static final QProperty<${property.rawType!}> ${property.name} = ${entity.simpleName}.getRequiredProperty("${property.path}");
</#list>


}
