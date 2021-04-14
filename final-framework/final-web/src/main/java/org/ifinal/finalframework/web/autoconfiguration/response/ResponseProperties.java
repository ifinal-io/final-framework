package org.ifinal.finalframework.web.autoconfiguration.response;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.ifinal.finalframework.core.annotation.result.Responsible;

import java.io.Serializable;

import lombok.Data;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = ResponseProperties.WEB_RESPONSE_STATUS_PREFIX)
public class ResponseProperties implements Serializable {

    static final String WEB_RESPONSE_STATUS_PREFIX = "final.web.response";

    /**
     * 同步{@link Responsible}状态到{@link javax.servlet.ServletResponse}
     */
    private boolean syncStatus = true;

}
