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

package org.finalframework.data.entity;


import org.finalframework.data.annotation.Creator;
import org.finalframework.data.annotation.LastModifier;
import org.finalframework.data.annotation.Reference;
import org.finalframework.data.annotation.Transient;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:23:19
 * @since 1.0
 */
@Transient
public class AbsRecord extends AbsEntity implements IRecord<Long, AbsUser> {

    @Creator
    @Reference(properties = {"id", "name"})
    private AbsUser creator;
    @LastModifier
    @Reference(properties = {"id", "name"})
    private AbsUser lastModifier;

    @Override
    public AbsUser getCreator() {
        return creator;
    }

    @Override
    public void setCreator(AbsUser creator) {
        this.creator = creator;
    }

    @Override
    public AbsUser getLastModifier() {
        return lastModifier;
    }

    @Override
    public void setLastModifier(AbsUser lastModifier) {
        this.lastModifier = lastModifier;
    }
}

