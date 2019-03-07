package com.ilikly.finalframework.mybatis;

import com.ilikly.finalframework.data.annotation.PrimaryKey;
import com.ilikly.finalframework.data.annotation.ReferenceColumn;
import com.ilikly.finalframework.data.annotation.Table;
import lombok.Data;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-22 14:42:50
 * @since 1.0
 */
@Data
@Table(name = "test_bean")
public class Bean {
    @PrimaryKey
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    @ReferenceColumn(properties = {"id", "age"})
    private Bean bean;
}
