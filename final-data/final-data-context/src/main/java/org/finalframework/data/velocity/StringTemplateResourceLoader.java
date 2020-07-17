/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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

