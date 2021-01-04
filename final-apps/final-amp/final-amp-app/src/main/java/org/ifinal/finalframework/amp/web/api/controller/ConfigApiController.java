package org.ifinal.finalframework.amp.web.api.controller;

import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.annotation.resource.ResourceValue;
import org.ifinal.finalframework.context.resource.ResourceValueManager;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ConfigApiController.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@RestController
@ResourceValue("config")
@RequestMapping("/config")
public class ConfigApiController {

    @ResourceValue("name")
    public String name;

    @Resource
    private ResourceValueManager resourceValueManager;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/name")
    public String name() {
        return name;
    }

    @PostMapping("/refresh")
    public boolean refresh(String key, String value) {
        logger.info("refresh config: key={}, value={}", key, value);
        resourceValueManager.setValue(key, value);
        return true;
    }

    @GetMapping("/update")
    public void update(String key, String value) {
        List<ServiceInstance> instances = discoveryClient.getInstances("application");

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("key", key);
        request.add("value", value);

        for (final ServiceInstance instance : instances) {
            String url = instance.getUri() + "/config/refresh";
            Boolean result = restTemplate.postForObject(url, request, Boolean.class);
            logger.info("refresh config: url={},result={}", url, result);
        }

    }

}
