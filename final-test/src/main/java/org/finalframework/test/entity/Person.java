package org.finalframework.test.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.ToString;
import org.finalframework.data.annotation.*;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.entity.IEntity;
import org.finalframework.data.entity.enums.YN;

import javax.validation.constraints.NotNull;
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
@ToString(callSuper = true)
public class Person implements IEntity<Long> {
    private static final long serialVersionUID = -8785625823175210092L;
    @PrimaryKey(insertable = true)
    private Long id;
    @JsonView(Person.class)
    @NotNull
    @ColumnView(Person.class)
    private String name;
    @JsonView(Person.class)
    private int age;
    @JsonColumn
    @ColumnView(Person.class)
    private List<String> stringList;
    @JsonColumn
    private List<Integer> intList;
    //    @NonColumn
    @ReferenceColumn(mode = ReferenceMode.SIMPLE, properties = {"id", "name", "age"})
    private Person creator;
    @Transient
    private Date date = new Date();

    private YN yn;

    public YN getYn() {
        return yn;
    }
}
