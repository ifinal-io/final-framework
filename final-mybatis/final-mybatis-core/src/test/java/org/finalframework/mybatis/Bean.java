package org.finalframework.mybatis;

import lombok.Data;
import org.finalframework.data.annotation.PrimaryKey;
import org.finalframework.data.annotation.ReferenceColumn;
import org.finalframework.data.annotation.Table;

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
