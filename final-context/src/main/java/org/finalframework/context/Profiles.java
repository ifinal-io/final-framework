package org.finalframework.context;

import org.springframework.context.annotation.Profile;

/**
 * 常用环境变量
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-24 15:53:20
 * @see Profile
 * @since 1.0
 */
public final class Profiles {

    public static final String LOCAL = "local";
    public static final String DEV = "dev";
    public static final String TEST = "test";
    public static final String PRODUCT = "product";

    private Profiles() {
        throw new IllegalArgumentException("Profiles 不支持实例化");
    }

}
