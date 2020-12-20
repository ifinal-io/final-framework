package org.ifinal.finalframework.amp.client;

import java.util.Map;
import javax.validation.constraints.NotEmpty;
import org.ifinal.finalframework.amp.entity.DataSource;

/**
 * AmpService.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AmpService {

    Map<String, DataSource> getDataSources(@NotEmpty String project, @NotEmpty String application);

}
