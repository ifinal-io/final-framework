package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.mybatis.holder.PropertyHolder;
import cn.com.likly.finalframework.mybatis.holder.TableHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 22:14
 * @since 1.0
 */
public interface PropertyHolderFactory {

    <T extends Entity> PropertyHolder create(TableHolder tableHolder, Field field, Method getter);

}
