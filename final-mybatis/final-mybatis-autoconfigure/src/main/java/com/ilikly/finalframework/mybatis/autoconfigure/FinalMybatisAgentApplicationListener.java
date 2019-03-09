package com.ilikly.finalframework.mybatis.autoconfigure;

import com.ilikly.finalframework.data.mapping.generator.ColumnGeneratorRegistry;
import com.ilikly.finalframework.mybatis.agent.MybatisAgent;
import com.ilikly.finalframework.mybatis.generator.BaseColumnGenerator;
import com.ilikly.finalframework.mybatis.generator.DefaultColumnGeneratorModule;
import com.ilikly.finalframework.spring.coding.ApplicationEventListener;
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
        ColumnGeneratorRegistry.getInstance().setDefaultColumnGenerator(BaseColumnGenerator.INSTANCE);
        ColumnGeneratorRegistry.getInstance().registerColumnModule(new DefaultColumnGeneratorModule());
        MybatisAgent.getInstance().agent();
    }
}
