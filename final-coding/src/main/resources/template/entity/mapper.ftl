package ${mapperPackage};

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.Entity;
import cn.com.likly.finalframework.mybatis.mapper.AbsMapper;
import org.apache.ibatis.annotations.*;


import ${entityPackage}.${entityName};

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Mapper
public interface ${mapperName} extends AbsMapper<${idProperty.type}, ${entityName}> {


}
