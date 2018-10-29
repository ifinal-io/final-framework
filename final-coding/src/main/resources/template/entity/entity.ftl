package ${package};

import ${entity.package}.${entity.simpleName};
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.domain.QProperty;

import javax.annotation.Generated;

@Generated("cn.com.likly.finalframework.coding.EntityProcessor")
public final class ${name}{

    public static final ${name} ${entity.simpleName} = new ${name}();

    private ${name}(){}

    public final Entity<${entity.simpleName}> holder = Entity.from(${entity.simpleName}.class);
<#list entity.properties as property>
    public final QProperty ${property.name} = (QProperty) holder.getRequiredPersistentProperty("${property.name}");
</#list>


}
