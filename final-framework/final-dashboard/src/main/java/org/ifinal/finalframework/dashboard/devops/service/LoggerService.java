package org.ifinal.finalframework.dashboard.devops.service;

import java.util.List;
import org.ifinal.finalframework.dashboard.devops.model.LoggerModel;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface LoggerService {

    List<LoggerModel> query();

    void setLevel(String name, String level);

}
