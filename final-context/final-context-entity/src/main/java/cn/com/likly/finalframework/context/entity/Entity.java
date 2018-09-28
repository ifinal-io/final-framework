package cn.com.likly.finalframework.context.entity;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:06
 * @since 1.0
 */
public interface Entity<ID> {

    ID getId();

    void setId(ID id);
}
