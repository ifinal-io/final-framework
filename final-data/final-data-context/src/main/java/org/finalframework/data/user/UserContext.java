package org.finalframework.data.user;

import org.finalframework.data.entity.IUser;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:34:33
 * @since 1.0
 */
public interface UserContext<USER extends IUser<?>> {

    USER getUser();
}
