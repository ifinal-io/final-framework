package org.ifinal.finalframework.dashboard.devops.service.impl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.ifinal.finalframework.dashboard.devops.model.LoggerModel;
import org.ifinal.finalframework.dashboard.devops.service.LoggerService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
class LoggerServiceImpl implements LoggerService {

    private static final List<String> filters = Arrays.asList(
            "org.springframework",
            "org.apache",
            "org.thymeleaf",
            "org.hibernate",
            "reactor",
            "io",
            "nz",
            "com.zaxxer"
    );
    private final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Override
    public List<LoggerModel> query() {
        return loggerContext.getLoggerList()
                .stream()
                .filter(logger -> {

                    for (String filter : filters) {
                        if (logger.getName().startsWith(filter)) {
                            return false;
                        }
                    }


                    return true;
                })
                .map(logger -> {
                    LoggerModel model = new LoggerModel();
                    model.setName(logger.getName());
                    Optional.ofNullable(logger.getLevel())
                            .ifPresent(level -> model.setLevel(level.toString()));
                    return model;
                })
//                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public void setLevel(String name, String level) {
        Logger logger = loggerContext.getLogger(name);
        logger.setLevel(Level.toLevel(level));
    }
}
