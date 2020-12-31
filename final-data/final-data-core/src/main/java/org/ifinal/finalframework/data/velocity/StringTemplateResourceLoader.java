package org.ifinal.finalframework.data.velocity;

import java.io.Reader;
import java.io.StringReader;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ExtProperties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class StringTemplateResourceLoader extends ResourceLoader {

    @Override
    public void init(final ExtProperties configuration) {
        // do nothing
    }

    @Override
    public Reader getResourceReader(final String source, final String encoding) {

        if (source == null || source.length() == 0) {
            throw new ResourceNotFoundException("Need to specify a template name!");
        }
        return new StringReader(source);
    }

    @Override
    public boolean isSourceModified(final Resource resource) {

        return false;
    }

    @Override
    public long getLastModified(final Resource resource) {

        return 0;
    }

}

