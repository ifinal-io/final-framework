package org.finalframework.dashboard.devops.service;

import org.finalframework.dashboard.devops.model.LoggerModel;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/22 15:10:30
 * @since 1.0
 */
public interface LoggerService {

    List<LoggerModel> query();

    void setLevel(String name, String level);
}
