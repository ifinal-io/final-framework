package org.ifinal.finalframework.boot.autoconfigure.web.response;

import java.io.Serializable;
import lombok.Data;
import org.ifinal.finalframework.origin.result.Responsible;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
