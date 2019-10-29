package org.finalframework.test.dao.mapper;


import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-27 02:12:49
 * @since 1.0
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:final.entities.vm");
        System.out.println(resources);
    }
}

