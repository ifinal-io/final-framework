package org.finalframework.dashboard.devops.controller.api;

import org.finalframework.dashboard.devops.model.LoggerModel;
import org.finalframework.dashboard.devops.service.LoggerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/22 15:15:47
 * @since 1.0
 */
@RestController
@RequestMapping("/api/devops/logger")
public class LoggerApiController {

    @Resource
    private LoggerService loggerService;

    @GetMapping
    public List<LoggerModel> query() {
        return loggerService.query();
    }

}
