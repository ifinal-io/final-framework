package org.finalframework.mybatis.coding.mapper.xml;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 19:09:48
 * @since 1.0
 */
public interface Element extends Comparable<Element> {
    default String name() {
        return type().getName();
    }

    ElementType type();

    @Override
    default int compareTo(Element o) {
        return type().order.compareTo(o.type().order);
    }

    enum ElementType implements Comparable<ElementType> {
        RESULT_MAP("resultMap", 0),
        ID_RESULT("id", 1),
        RESULT("result", 2),
        ASSOCIATION("association", 3);

        private final String name;
        private final Integer order;

        ElementType(String name, Integer order) {
            this.name = name;
            this.order = order;
        }

        public String getName() {
            return name;
        }


    }
}
