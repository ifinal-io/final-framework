

package org.finalframework.spring.web.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-14 11:04:03
 * @since 1.0
 */
@ConfigurationProperties(prefix = ResponseBodyAdviceProperties.RESPONSE_PROPERTIES)
public class ResponseBodyAdviceProperties {
    static final String RESPONSE_PROPERTIES = "final.response";
    /**
     * 是否同步业务状态到Response中
     */
    private boolean syncStatus = true;

    public boolean isSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(boolean syncStatus) {
        this.syncStatus = syncStatus;
    }
}
