package org.finalframework.boot.autoconfigure.web.response;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/26 20:33:56
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = ResponseProperties.WEB_RESPONSE_STATUS_PREFIX)
public class ResponseProperties implements Serializable {
    static final String WEB_RESPONSE_STATUS_PREFIX = "final.web.response";

    /**
     * 同步{@link org.finalframework.annotation.result.Responsible}状态到{@link javax.servlet.ServletResponse}
     */
    private boolean syncStatus = true;
}
