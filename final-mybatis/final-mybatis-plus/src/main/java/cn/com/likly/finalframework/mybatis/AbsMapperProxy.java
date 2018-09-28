package cn.com.likly.finalframework.mybatis;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.mybatis.annotation.Command;
import cn.com.likly.finalframework.mybatis.criteria.Criteria;
import cn.com.likly.finalframework.mybatis.criteria.SetCriteria;
import cn.com.likly.finalframework.mybatis.holder.EntityHolder;
import cn.com.likly.finalframework.mybatis.mapper.AbsMapper;
import cn.com.likly.finalframework.mybatis.mapper.DefaultMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 20:39
 * @since 1.0
 */
public class AbsMapperProxy<ID, T extends Entity<ID>, MAPPER extends AbsMapper<ID, T>> implements InvocationHandler {
    private final DefaultMapper<ID, T> defaultMapper;
    private final Object target;
    private final EntityHolder holder;
    private final Object proxy;

    public AbsMapperProxy(DefaultMapper<ID, T> defaultMapper, Object target, EntityHolder holder) {
        this.defaultMapper = defaultMapper;
        this.target = target;
        this.holder = holder;
        this.proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public MAPPER getMapper() {
        return (MAPPER) proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Command command = method.getAnnotation(Command.class);

        if (command == null) return method.invoke(target, args);

        switch (command.value()) {
            case INSERT_ARRAY:
                switch (holder.getTable().getPrimaryKey()) {
                    case AUTO:
                        return defaultMapper.insert(command.value().name(), holder, (T[]) args[0]);
                    case UUID:
                        return defaultMapper.insertUuid(command.value().name(), holder, (T[]) args[0]);
                    case UUIDMD5:
                        return defaultMapper.insertMd5(command.value().name(), holder, (T[]) args[0]);
                    case OTHER:
                        return defaultMapper.insertOther(command.value().name(), holder, (T[]) args[0]);
                }
                break;
            case INSERT_LIST:
                switch (holder.getTable().getPrimaryKey()) {
                    case AUTO:
                        return defaultMapper.insert(command.value().name(), holder, (List<T>) args[0]);
                    case UUID:
                        return defaultMapper.insertUuid(command.value().name(), holder, (List<T>) args[0]);
                    case UUIDMD5:
                        return defaultMapper.insertMd5(command.value().name(), holder, (List<T>) args[0]);
                    case OTHER:
                        return defaultMapper.insertOther(command.value().name(), holder, (List<T>) args[0]);
                }
                break;
            case UPDATE_ENTITY:
                return defaultMapper.update(command.value().name(), holder, (T) args[0]);
            case UPDATE_CRITERIA:
                return defaultMapper.update(command.value().name(), holder, (Criteria) args[0]);
            case UPDATE_ID_SETS:
                return defaultMapper.update(command.value().name(), holder, (ID) args[0], (SetCriteria[]) args[1]);
            case UPDATE_CRITERIA_SETS:
                return defaultMapper.update(command.value().name(), holder, (Criteria) args[0], (SetCriteria[]) args[1]);

            case DELETE_ID_ARRAY:
                return defaultMapper.delete(command.value().name(), holder, (ID[]) args[0]);
            case DELETE_ID_LIST:
                return defaultMapper.delete(command.value().name(), holder, (List<ID>) args[0]);
            case DELETE_ENTITIES:
                return defaultMapper.delete(command.value().name(), holder, (T[]) args[0]);
            case DELETE_CRITERIA:
                return defaultMapper.delete(command.value().name(), holder, (Criteria) args[0]);

            case SELECT_ID_ARRAY:
                return defaultMapper.select(command.value().name(), holder, (ID[]) args[0]);
            case SELECT_ID_LIST:
                return defaultMapper.select(command.value().name(), holder, (List<ID>) args[0]);
            case SELECT_CRITERIA:
                return defaultMapper.select(command.value().name(), holder, (Criteria) args[0]);
            case SELECT_ONE_ID:
                return defaultMapper.selectOne(command.value().name(), holder, (ID) args[0]);
            case SELECT_ONE_CRITERIA:
                return defaultMapper.selectOne(command.value().name(), holder, (Criteria) args[0]);
            case SELECT_COUNT:
                return defaultMapper.selectCount(command.value().name(), holder, (Criteria) args[0]);

        }


        return null;


    }
}
