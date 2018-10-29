package cn.com.likly.finalframework.data.entity;

import cn.com.likly.finalframework.data.annotation.MapperEntity;
import cn.com.likly.finalframework.data.entity.enums.YN;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:25
 * @since 1.0
 */
@Data
@MapperEntity
@EqualsAndHashCode(callSuper = true)
public class Person extends Domain {


    private static final long serialVersionUID = -8785625823175210092L;

    private List<String> stringList;
    private List<Integer> intList;
    private Map<String, Integer> maps;
    private YN yn = YN.YES;
    //    private String test;
}
