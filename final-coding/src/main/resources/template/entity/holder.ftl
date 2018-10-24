package ${holderPackage};

import ${entityPackage}.${entityName};
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;

public final class ${holderName}{

    public static final EntityHolder<${entityName}> entity = EntityHolder.from(${entityName}.class);
<#list properties as property>
    public static final PropertyHolder ${property.name} = entity.getRequiredPersistentProperty("${property.name}");
</#list>

}
