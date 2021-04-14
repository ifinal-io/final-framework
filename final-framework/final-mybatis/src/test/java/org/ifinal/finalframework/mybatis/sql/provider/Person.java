package org.ifinal.finalframework.mybatis.sql.provider;

import org.ifinal.finalframework.core.annotation.IView;
import org.ifinal.finalframework.core.annotation.lang.Transient;
import org.ifinal.finalframework.data.annotation.AbsEntity;
import org.ifinal.finalframework.data.annotation.Column;
import org.ifinal.finalframework.data.annotation.Function;
import org.ifinal.finalframework.data.annotation.Reference;
import org.ifinal.finalframework.data.annotation.View;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

