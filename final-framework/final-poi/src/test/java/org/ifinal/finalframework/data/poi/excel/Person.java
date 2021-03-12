package org.ifinal.finalframework.data.poi.excel;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Person.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
class Person {
    private String name;
    private Integer age;
    private Date birthday;
}
