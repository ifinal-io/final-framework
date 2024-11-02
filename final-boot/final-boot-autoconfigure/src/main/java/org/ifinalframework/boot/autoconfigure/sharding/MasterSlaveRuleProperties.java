/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.boot.autoconfigure.sharding;

import java.io.Serializable;
import java.util.List;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class MasterSlaveRuleProperties implements Serializable {

    private static final long serialVersionUID = -4796666194553926329L;

    private String name;

    private String master;

    private List<String> slaves;

    public String getName() {
        return name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(final String master) {

        this.master = master;
    }

    public List<String> getSlaves() {
        return slaves;
    }

    public void setSlaves(final List<String> slaves) {

        this.slaves = slaves;
    }

}

