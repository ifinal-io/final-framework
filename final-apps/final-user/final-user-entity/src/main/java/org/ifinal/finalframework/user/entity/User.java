package org.ifinal.finalframework.user.entity;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.core.IUser;
import org.ifinal.finalframework.annotation.data.AbsEntity;

/**
 * User.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
public class User extends AbsEntity implements IUser<Long> {

    @NotEmpty
    private String name;

    @NotEmpty
    private String account;

    @NotEmpty
    private String password;

}
