package org.ifinal.finalframework.dashboard.mybaits.controller.api;

import javax.annotation.Resource;
import org.ifinal.finalframework.dashboard.mybaits.service.XmlMapperService;
import org.ifinal.finalframework.dashboard.mybaits.service.query.XmlMapperQuery;
import org.ifinal.finalframework.web.response.annotation.ResponseIgnore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/mybatis/xml", produces = MediaType.APPLICATION_XML_VALUE)
@ResponseIgnore
public class XmlMapperApiController {

    @Resource
    private XmlMapperService xmlMapperService;

    @GetMapping
    public String xml(final XmlMapperQuery query) {

        return xmlMapperService.xml(query);
    }

}
