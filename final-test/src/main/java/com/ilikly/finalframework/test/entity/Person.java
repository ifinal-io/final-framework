package com.ilikly.finalframework.test.entity;

import com.ilikly.finalframework.data.annotation.Entity;
import com.ilikly.finalframework.data.annotation.JsonColumn;
import com.ilikly.finalframework.data.annotation.MultiColumn;
import com.ilikly.finalframework.data.annotation.NonColumn;
import com.ilikly.finalframework.data.entity.AbsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:25
 * @since 1.0
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Person extends AbsEntity {
    private static final long serialVersionUID = -8785625823175210092L;
    private String name;
    private Integer age;
    @JsonColumn
    private List<String> stringList;
    @JsonColumn
    private List<Integer> intList;
    //    @NonColumn
    @MultiColumn(properties = {"id", "name", "age"})
    private Person creator;
    @NonColumn
    private Date date = new Date();

}
