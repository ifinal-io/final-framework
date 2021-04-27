/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.velocity;

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

