package org.ifinal.finalframework.dashboard.devops.controller.api;

import org.ifinal.finalframework.dashboard.devops.model.LoggerModel;
import org.ifinal.finalframework.dashboard.devops.service.LoggerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

    @PostMapping
    public void level(final String name, final String level) {

        loggerService.setLevel(name, level);
    }


}
