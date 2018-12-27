package ${package};

import ${entity.package}.${entity.simpleName};
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.query.QEntity;
import cn.com.likly.finalframework.data.query.QProperty;

import javax.annotation.Generated;

@Generated("cn.com.likly.finalframework.coding.EntityProcessor")
public final class ${name}{

    private ${name}(){}

public static final QEntity<${idProperty.rawType},${entity.simpleName}> ${entity.simpleName} = QEntity.from(${entity.simpleName}.class);
<#list properties as property>
    public static final QProperty<${property.rawType}> ${property.name} = ${entity.simpleName}.getRequiredProperty("${property.path}");
</#list>


}
