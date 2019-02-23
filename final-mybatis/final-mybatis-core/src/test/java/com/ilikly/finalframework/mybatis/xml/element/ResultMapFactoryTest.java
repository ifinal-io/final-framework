package com.ilikly.finalframework.mybatis.xml.element;

import com.ilikly.finalframework.mybatis.Bean;
import org.junit.Test;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-22 14:42:29
 * @since 1.0
 */
public class ResultMapFactoryTest {

    ResultMapFactory factory = new ResultMapFactory();

    @Test
    public void create() {
        final ResultMap resultMap = factory.create(Bean.class);


        System.out.println(resultMap);
    }

}