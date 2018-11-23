package cn.com.likly.finalframework.data.entity;

import cn.com.likly.finalframework.data.annotation.JsonColumn;
import cn.com.likly.finalframework.data.annotation.MapperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:25
 * @since 1.0
 */
@Data
@MapperEntity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Person extends BaseEntity {
    private static final long serialVersionUID = -8785625823175210092L;

    @JsonColumn
    private List<String> stringList;
    @JsonColumn
    private List<Integer> intList;
}
