package org.ifinal.finalframework.amp.web.api.controller;

import javax.annotation.Resource;
import org.ifinal.finalframework.amp.entity.Project;
import org.ifinal.finalframework.annotation.data.AbsUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ProjectApiControllerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootTest
class ProjectApiControllerTest {

    @Resource
    private ProjectApiController projectApiController;

    private final AbsUser user;

    {
        user = new AbsUser();
        user.setId(-1L);
        user.setName("ROOT");
    }

    @Test
    void put() {
        Project project = new Project();
        project.setCode("final-amp");
        project.setName("应用管理平台");
        project.setDescription("应用管理平台");
        project.setCreator(user);
        projectApiController.put(project);

    }

}