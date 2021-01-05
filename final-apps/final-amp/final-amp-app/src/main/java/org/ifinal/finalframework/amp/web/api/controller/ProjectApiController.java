package org.ifinal.finalframework.amp.web.api.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.amp.entity.Project;
import org.ifinal.finalframework.amp.service.ProjectService;
import org.ifinal.finalframework.json.Json;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProjectApiController.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/projects")
public class ProjectApiController {

    @Resource
    private ProjectService projectService;

    @PutMapping
    public boolean put(final Project project) {
        int rows = projectService.insert(project);
        logger.info("create project: {}", Json.toJson(project));
        return true;
    }

}
