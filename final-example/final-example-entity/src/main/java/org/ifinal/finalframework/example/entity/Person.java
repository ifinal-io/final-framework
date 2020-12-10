package org.ifinal.finalframework.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.core.IView;
import org.ifinal.finalframework.annotation.data.AbsEntity;
import org.ifinal.finalframework.annotation.data.Reference;
import org.ifinal.finalframework.annotation.data.View;
import org.ifinal.finalframework.annotation.sharding.InlineShardingStrategy;
import org.ifinal.finalframework.annotation.sharding.ShardingScope;
import org.ifinal.finalframework.annotation.sharding.ShardingTable;

import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
@ShardingTable(logicTables = {"person"},actualDataNodes = "ds${0..1}.${logicTable}_0${0..1}")
@InlineShardingStrategy(scope = ShardingScope.DATABASE,columns = "age",expression = "ds${age % 2}")
@InlineShardingStrategy(scope = ShardingScope.TABLE,columns = "age",expression = "${logicTable}_0${age % 2}")
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
