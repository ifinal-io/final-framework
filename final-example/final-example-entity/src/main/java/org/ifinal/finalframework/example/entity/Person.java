package org.ifinal.finalframework.example.entity;

import lombok.Data;
import org.ifinal.finalframework.core.annotation.IView;
import org.ifinal.finalframework.data.annotation.AbsEntity;
import org.ifinal.finalframework.data.annotation.Reference;
import org.ifinal.finalframework.data.annotation.View;
import org.ifinal.finalframework.sharding.annotation.ShardingTable;
import org.ifinal.finalframework.sharding.annotation.TableInlineShardingStrategy;

import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@ShardingTable(logicTables = {"person","person_copy"},actualDataNodes = "ds0.${logicTable}_${00.99}")
@TableInlineShardingStrategy(columns = "name",expression = "ds0.${logicTable}_${name}")
public class Person extends AbsEntity {

    @View(RegisterView.class)
    private String account;
    @View(RegisterView.class)
    private String password;
    private Boolean admin;
    private String name;
    private Integer age;
    private List<Integer> intList;
    private Map<String, String> map;
    @Reference(properties = {"id", "name"})
    private Person creator;


    public interface RegisterView extends IView {
    }

    public interface ShareView extends IView {
    }

}
