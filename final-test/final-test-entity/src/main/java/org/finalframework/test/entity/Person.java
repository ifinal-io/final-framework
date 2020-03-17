package org.finalframework.test.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.data.annotation.*;
import org.finalframework.data.entity.AbsRecord;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:25
 * @since 1.0
 */
public class Person extends AbsRecord {
    private static final long serialVersionUID = -8785625823175210092L;
    //    @PrimaryKey(insertable = true)
//    private Long id;
    @JsonView(Person.class)
    @NotNull
    @View(Person.class)
    private String name;
    @JsonView(Person.class)
    private int age;
    @ReadOnly
    @Function(reader = "MAX(age)")
    @View(Person.class)
    private int maxAge;
    @View(Person.class)
    @Json("jsonList")
    private List<String> stringList;
    @Column
    private List<Integer> intList;
    private Map<String, Object> properties;
    //    @NonColumn
    @Transient
    private Date date = new Date();

//    private YN yn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @WriteOnly
    public String getExtName() {
        return name;
    }

    @Virtual
    public Integer getExtAge() {
        return 1;
    }
}
