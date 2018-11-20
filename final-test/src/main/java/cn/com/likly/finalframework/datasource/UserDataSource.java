package cn.com.likly.finalframework.datasource;

import cn.com.likly.finalframework.data.mybatis.annotation.DataSource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-08 10:49
 * @since 1.0
 */
@DataSource(prefix = "cn.com.likly.user.datasource",
                  mapperLocations = "classpath*:cn/com/likly/user/*.xml",
                  basePackages = {"cn.com.likly.user.mapper", "cn.com.likly.user.ext.mapper"})
public interface UserDataSource {
}
