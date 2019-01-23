package ${package};

import ${entity.package}.${entity.simpleName};
import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.QEntity;

import javax.annotation.Generated;

@Generated("com.ilikly.finalframework.coding.EntityProcessor")
public final class ${name}{

    private ${name}(){}

public static final QEntity ${entity.simpleName} = QEntity.from(${entity.simpleName}.class);
<#list properties as property>
    public static final QProperty ${property.name} = ${entity.simpleName}.getRequiredProperty("${property.path}");
</#list>


}
