package org.ifinal.finalframework.data.velocity;


import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ExtProperties;

import java.io.Reader;
import java.io.StringReader;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class StringTemplateResourceLoader extends ResourceLoader {
    @Override
    public void init(ExtProperties configuration) {
        // do nothing
    }

    @Override
    public Reader getResourceReader(String source, String encoding) {
        if (source == null || source.length() == 0) {
            throw new ResourceNotFoundException("Need to specify a template name!");
        }
        return new StringReader(source);
    }

    @Override
    public boolean isSourceModified(Resource resource) {
        return false;
    }

    @Override
    public long getLastModified(Resource resource) {
        return 0;
    }
}

