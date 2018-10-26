package ${qentityPackage};

import ${entityPackage}.${entityName};
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.domain.QProperty;

public final class ${qentityName}{

    private ${qentityName}(){}

    public final Entity<${entityName}> holder = Entity.from(${entityName}.class);
<#list properties as property>
    public final QProperty ${property.name} = (QProperty)holder.getRequiredPersistentProperty("${property.name}");
</#list>


    public static ${qentityName} ${instanceName} = new ${qentityName}();
}
