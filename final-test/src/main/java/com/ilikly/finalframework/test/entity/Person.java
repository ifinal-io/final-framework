package com.ilikly.finalframework.test.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.ilikly.finalframework.data.annotation.*;
import com.ilikly.finalframework.data.annotation.enums.ReferenceMode;
import com.ilikly.finalframework.data.entity.AbsEntity;
import com.ilikly.finalframework.data.result.Result;
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
public class Person extends AbsEntity implements Result.View {
    private static final long serialVersionUID = -8785625823175210092L;
    @JsonView(Person.class)
    @ColumnView(Result.View.class)
    private String name;
    @JsonView(Person.class)
    @ColumnView(Result.View.class)
    private Integer age;
    @JsonColumn
    @ColumnView(Person.class)
    private List<String> stringList;
    @JsonColumn
    private List<Integer> intList;
    //    @NonColumn
    @ReferenceColumn(mode = ReferenceMode.SIMPLE, properties = {"id", "name", "age"})
    private Person creator;
    @NonColumn
    private Date date = new Date();

}
