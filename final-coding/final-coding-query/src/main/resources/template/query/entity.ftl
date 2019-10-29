package ${package};

import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.AbsQEntity;
import ${entity.package}.${entity.simpleName};

import javax.annotation.Generated;

@Generated("org.finalframework.coding.EntityProcessor")
public final class ${name} extends AbsQEntity<${entity.idProperty.type?replace("java.lang.","")},${entity.simpleName}>{

    public ${name}() {
        super(${entity.simpleName}.class);
    }

    public ${name}(String table) {
        super(${entity.simpleName}.class, table);
    }

    public static final ${name} ${entity.simpleName} = new ${name}();
<#list properties as property>
<#--    /**-->
<#--     * @see ${entity.simpleName}#${property.path?replace(".","#")}-->
<#--     */-->
    public static final QProperty<${property.type?replace("java.lang.","")}> ${property.name} = ${entity.simpleName}.getRequiredProperty("${property.path}");
</#list>

    @Override
    protected void initProperties() {
        super.initProperties();

<#list properties as property>
        addProperty(
                QProperty.builder(this,${property.type?replace("java.lang.","")}.class)
                        .path("${property.path}").name("${property.name}").column("${property.column}")
                        .idProperty(${property.idProperty?c}).persistentType(PersistentType.${property.persistentType})
                        .insertable(${property.insertable?c}).updatable(${property.updatable?c}).selectable(${property.selectable?c})
                        .build()
        );
</#list>

    }

<#--public static final QEntity<${entity.idProperty.rawType?replace("java.lang.","")},${entity.simpleName}> ${entity.simpleName} = QEntity.from(${entity.simpleName}.class);-->
<#--<#list properties as property>-->
<#--    public static final QProperty<${property.rawType?replace("java.lang.","")}> ${property.name} = ${entity.simpleName}.getRequiredProperty("${property.path}");-->
<#--</#list>-->


}
