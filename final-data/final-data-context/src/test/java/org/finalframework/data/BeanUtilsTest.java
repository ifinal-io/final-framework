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

package org.finalframework.data;


import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-16 13:05:26
 * @since 1.0
 */
public class BeanUtilsTest {

    public Boolean tag;
    private boolean isTag;

    public Boolean getTag() {
        return tag;
    }

    public boolean isTag() {
        return isTag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    public void setTag(boolean tag) {
        isTag = tag;
    }

    @Test
    public void test() {
        final Source source = new Source();
        source.setTag(true);
        final Target target = new Target();
        BeanUtils.copyProperties(source, target);
        System.out.println(target.getTag());
    }

    public static class Source {
        private boolean isTag;

        public boolean isTag() {
            return isTag;
        }

        public void setTag(boolean tag) {
            isTag = tag;
        }
    }

    public static class Target {
        private Boolean tag;

        public Boolean getTag() {
            return tag;
        }

        public void setTag(Boolean tag) {
            this.tag = tag;
        }
    }

}

