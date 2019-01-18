package com.ilikly.finalframework.test.dao;

import com.ilikly.finalframework.logging.appender.AbsAppender;
import com.ilikly.finalframework.logging.entity.LoggingEvent;
import com.ilikly.finalframework.test.dao.mapper.LoggingEventMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-16 21:57:36
 * @since 1.0
 */
@Configuration
public class LoggerAppender extends AbsAppender implements ApplicationContextAware {

    private static LoggingEventMapper loggingEventMapper;
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        loggingEventMapper = applicationContext.getBean(LoggingEventMapper.class);
    }

    @Override
    protected void append(LoggingEvent event) {
        if (loggingEventMapper != null) {
            try {
                loggingEventMapper.insert(event);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
