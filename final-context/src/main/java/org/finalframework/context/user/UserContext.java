

package org.finalframework.context.user;

import org.finalframework.annotation.IUser;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:34:33
 * @since 1.0
 */
@FunctionalInterface
public interface UserContext<USER extends IUser<?>> {

    /**
     * return the user.
     *
     * @return the user.
     */
    @Nullable
    USER getUser();


}
