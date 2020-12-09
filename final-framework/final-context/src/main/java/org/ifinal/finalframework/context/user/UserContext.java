package org.ifinal.finalframework.context.user;

import org.ifinal.finalframework.core.annotation.IUser;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface UserContext<U extends IUser<?>> {

    /**
     * return the user.
     *
     * @return the user.
     */
    @Nullable
    U getUser();


}
