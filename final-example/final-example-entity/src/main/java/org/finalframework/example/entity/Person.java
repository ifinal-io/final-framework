package org.finalframework.example.entity;

import lombok.Data;
import org.finalframework.annotation.data.AbsEntity;
import org.finalframework.annotation.data.Reference;

import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/12 16:53:31
 * @since 1.0
 */
@Data
public class Person extends AbsEntity {

    private List<Integer> intList;
    private Map<String, String> map;
    @Reference(properties = {"id", "intList"})
    private Person creator;

}
