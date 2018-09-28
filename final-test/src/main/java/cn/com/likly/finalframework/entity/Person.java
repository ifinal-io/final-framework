package cn.com.likly.finalframework.entity;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.context.entity.enums.YN;
import cn.com.likly.finalframework.mybatis.annotation.ID;
import cn.com.likly.finalframework.mybatis.annotation.JsonColumn;
import cn.com.likly.finalframework.mybatis.annotation.TableColumn;
import cn.com.likly.finalframework.mybatis.annotation.enums.JdbcType;
import lombok.Data;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:25
 * @since 1.0
 */
@Data
public class Person implements Entity<Long> {
    @ID
    private Long id;
    @TableColumn(jdbcType = JdbcType.DEFAULT)
    private List<String> stringList;
    @JsonColumn
    private List<Integer> intList;
    private YN yn = YN.YES;
}
