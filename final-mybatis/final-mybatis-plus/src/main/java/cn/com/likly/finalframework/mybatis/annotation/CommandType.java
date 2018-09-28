package cn.com.likly.finalframework.mybatis.annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 18:07
 * @since 1.0
 */
public enum CommandType {
    INSERT_ARRAY,
    INSERT_LIST,

    UPDATE_ENTITY,
    UPDATE_CRITERIA,
    UPDATE_ID_SETS,
    UPDATE_CRITERIA_SETS,

    DELETE_ID_ARRAY,
    DELETE_ID_LIST,
    DELETE_ENTITIES,
    DELETE_CRITERIA,

    SELECT_ID_ARRAY,
    SELECT_ID_LIST,
    SELECT_CRITERIA,
    SELECT_ONE_ID,
    SELECT_ONE_CRITERIA,
    SELECT_COUNT
}
