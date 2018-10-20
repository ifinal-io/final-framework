package cn.com.likly.finalframework.data.entity;

import cn.com.likly.finalframework.data.annotation.Entity;
import cn.com.likly.finalframework.data.entity.enums.YN;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Person extends Domain {
    private List<String> stringList;
    private List<Integer> intList;
    private YN yn = YN.YES;
}
