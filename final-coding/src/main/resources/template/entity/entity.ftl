package ${package};

import ${entity.package}.${entity.simpleName};
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.domain.QProperty;

import javax.annotation.Generated;

@Generated("cn.com.likly.finalframework.coding.EntityProcessor")
public final class ${name}{

    private ${name}(){}

public static final Entity<${entity.simpleName}> ${entity.simpleName} = Entity.from(${entity.simpleName}.class);
<#list entity.properties as property>
    public static final QProperty ${property.name} = (QProperty) ${entity.simpleName}.getRequiredPersistentProperty("${property.name}");
</#list>


}
