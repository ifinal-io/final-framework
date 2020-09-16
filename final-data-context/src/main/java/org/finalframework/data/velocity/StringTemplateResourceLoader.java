package org.finalframework.data.velocity;


import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ExtProperties;

import java.io.Reader;
import java.io.StringReader;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 10:22:04
 * @since 1.0
 */
public class StringTemplateResourceLoader extends ResourceLoader {
    @Override
    public void init(ExtProperties configuration) {

    }

    @Override
    public Reader getResourceReader(String source, String encoding) throws ResourceNotFoundException {
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

