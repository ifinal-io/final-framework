package ${package};

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.IEntity;
import cn.com.likly.finalframework.data.mybatis.mapper.AbsMapper;
import org.apache.ibatis.annotations.*;


import ${entityPackage}.${entityName};

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;

@Mapper
@Generated("cn.com.likly.finalframework.coding.EntityProcessor")
public interface ${name} extends AbsMapper<${primaryKeyType}, ${entityName}> {


}
