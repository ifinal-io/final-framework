package org.ifinal.finalframework.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class BeanTypeHandlersTest {

    @Test
    void test() {
        final Source source = new Source();
        source.setTag(true);
        final Target target = new Target();
        BeanUtils.copyProperties(source, target);
        Assertions.assertEquals(true, target.getTag());
    }

    static class Source {

        private boolean isTag;

        public boolean isTag() {
            return isTag;
        }

        public void setTag(final boolean tag) {

            isTag = tag;
        }

    }

    static class Target {

        private Boolean tag;

        public Boolean getTag() {
            return tag;
        }

        public void setTag(final Boolean tag) {

            this.tag = tag;
        }

    }

}

