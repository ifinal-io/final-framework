package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.mybatis.holder.EntityHolder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 21:53
 * @since 1.0
 */
public interface ResultMapFactory {
    ResultMap create(EntityHolder holder, Configuration configuration);
}
