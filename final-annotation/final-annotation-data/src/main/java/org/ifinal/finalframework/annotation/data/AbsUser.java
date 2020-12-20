package org.ifinal.finalframework.annotation.data;

import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.core.IUser;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class AbsUser implements IUser<Long> {

    @PrimaryKey
    private Long id;

    private String name;

}

