package com.ilikly.finalframework.mybatis.application.listener;

import com.ilikly.finalframework.coding.plugins.spring.annotation.ApplicationEventListener;
import com.ilikly.finalframework.mybatis.agent.MybatisAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-25 23:13:53
 * @since 1.0
 */
@SuppressWarnings("unused")
@ApplicationEventListener
public class FinalMybatisSpringApplicationStartingListener implements ApplicationListener<ApplicationStartingEvent> {
    private static final Logger logger = LoggerFactory.getLogger(FinalMybatisSpringApplicationStartingListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        logger.info("do datasource agent on application starting event");
        MybatisAgent.getInstance().agent();
        logger.info("done datasource agent on application starting event");
    }
}
