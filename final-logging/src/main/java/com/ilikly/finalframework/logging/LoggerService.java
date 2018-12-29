package com.ilikly.finalframework.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.ilikly.finalframework.logging.entity.Logger;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-12 17:31:11
 * @since 1.0
 */
@Service
public class LoggerService {

    public List<Logger> resetLoggers() {
        List<Logger> loggers = getLoggers();
        if (loggers == null) return null;

        for (Logger logger : loggers) {
            ch.qos.logback.classic.Logger realLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(logger.getName());
            realLogger.setLevel(Level.INFO);
        }
        return loggers;

    }

    public List<Logger> getLoggers() {
        ILoggerFactory factory = LoggerFactory.getILoggerFactory();
        if (factory instanceof LoggerContext) {
            List<ch.qos.logback.classic.Logger> loggers = ((LoggerContext) factory).getLoggerList();
            return loggers.stream()
                    .map(it -> new Logger(it.getName(), it.getLevel() == null ? null : it.getLevel().levelStr))
                    .collect(Collectors.toList());
        }

        return null;
    }


}
