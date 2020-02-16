package org.finalframework.json.jackson;


import lombok.Data;
import org.finalframework.data.entity.AbsEntity;
import org.finalframework.data.entity.enums.YN;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-16 22:46:57
 * @since 1.0
 */
@Data
public class JacksonBean extends AbsEntity {
    private YN[] yns = YN.values();
}

