package cn.com.likly.finalframework.dao.mapper;

import cn.com.likly.finalframework.data.entity.Person;
import cn.com.likly.finalframework.mybatis.mapper.AbsMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:35
 * @since 1.0
 */
@Mapper
public interface PersonMapper extends AbsMapper<Long, Person> {
}
