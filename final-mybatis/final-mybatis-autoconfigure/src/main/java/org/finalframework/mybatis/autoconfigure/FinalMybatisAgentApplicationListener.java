package org.finalframework.mybatis.autoconfigure;

import org.finalframework.data.mapping.generator.ColumnGeneratorRegistry;
import org.finalframework.mybatis.generator.DefaultColumnGenerator;
import org.finalframework.mybatis.generator.DefaultColumnGeneratorModule;
import org.finalframework.spring.coding.ApplicationEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 15:22:13
 * @since 1.0
 */
@ApplicationEventListener
public class FinalMybatisAgentApplicationListener implements ApplicationListener<ApplicationStartingEvent> {

    private static final Logger logger = LoggerFactory.getLogger(FinalMybatisAgentApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        ColumnGeneratorRegistry.getInstance().setDefaultColumnGenerator(DefaultColumnGenerator.INSTANCE);
        ColumnGeneratorRegistry.getInstance().registerColumnModule(new DefaultColumnGeneratorModule());
//        MybatisAgent.getInstance().agent();
    }
}
