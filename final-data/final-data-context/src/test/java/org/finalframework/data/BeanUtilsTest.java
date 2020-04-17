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

