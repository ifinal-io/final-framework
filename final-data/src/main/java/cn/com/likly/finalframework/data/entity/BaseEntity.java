package cn.com.likly.finalframework.data.entity;

import cn.com.likly.finalframework.data.annotation.Column;
import cn.com.likly.finalframework.data.annotation.CreatedTime;
import cn.com.likly.finalframework.data.annotation.LastModifiedTime;
import cn.com.likly.finalframework.data.annotation.PrimaryKey;
import cn.com.likly.finalframework.data.entity.enums.YN;
import lombok.Data;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-20 17:17
 * @since 1.0
 */
@Data
public class BaseEntity implements Entity<Long> {

    private static final long serialVersionUID = -3500516904657883963L;

    @PrimaryKey
    private Long id;
    @CreatedTime
    private Date createdTime;
    @LastModifiedTime
    private Date lastModifiedTime;
    @Column
    private YN yn = YN.YES;
}

    


