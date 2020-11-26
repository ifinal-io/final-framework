package org.ifinal.finalframework.dashboard.devops.service;

import org.ifinal.finalframework.dashboard.devops.model.LoggerModel;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface LoggerService {

    List<LoggerModel> query();

    void setLevel(String name, String level);
}
