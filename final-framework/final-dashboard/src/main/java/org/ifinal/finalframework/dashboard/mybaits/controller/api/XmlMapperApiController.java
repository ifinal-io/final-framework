package org.ifinal.finalframework.dashboard.mybaits.controller.api;

import org.ifinal.finalframework.dashboard.mybaits.service.XmlMapperService;
import org.ifinal.finalframework.dashboard.mybaits.service.query.XmlMapperQuery;
import org.ifinal.finalframework.web.response.annotation.ResponseIgnore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String xml(XmlMapperQuery query) {
        return xmlMapperService.xml(query);
    }

}
