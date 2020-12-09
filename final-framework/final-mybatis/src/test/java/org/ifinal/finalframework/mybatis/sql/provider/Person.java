package org.ifinal.finalframework.mybatis.sql.provider;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ifinal.finalframework.core.annotation.IView;
import org.ifinal.finalframework.data.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Transient
public class Person extends AbsEntity {
//    @Virtual
@Column
public String vcolumn = "32";
    @View(IView.class)
    private String name;
    private Integer age;
    @Function(reader = "MAX(age) as ${column}")
    private Integer maxAge;
    @Reference(properties = {"id", "name"})
    private Person creator;
}

