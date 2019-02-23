package com.ilikly.finalframework.mybatis.builder;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.mybatis.xml.element.Association;
import com.ilikly.finalframework.mybatis.xml.element.Result;
import com.ilikly.finalframework.mybatis.xml.element.ResultMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 将一个{@link ResultMap}对象转换成{@literal resultMap}标签
 * <pre>
 *     <code>
 *          <resultMap id="****Map" type="">
 *              <id property="" column="" javaType="" jdbcType="" typeHandler=""/>
 *              <result property="" column="" javaType="" jdbcType="" typeHandler=""/>
 *              <result property="" column="" javaType="" jdbcType="" typeHandler=""/>
 *              <association property="" javaType="">
 *                   <id property="" column="" javaType="" jdbcType="" typeHandler=""/>
 *                   <result property="" column="" javaType="" jdbcType="" typeHandler=""/>
 *              </association>
 *          </resultMap>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-22 15:27:49
 * @since 1.0
 */
public class ResultMapMapperBuilder implements MapperBuilder {

    private final ResultMap resultMap;

    public ResultMapMapperBuilder(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public Element build(Document document) {
        return buildResultMapElement(document, resultMap);
    }


    private Element buildResultMapElement(Document document, ResultMap resultMap) {
        final Element element = document.createElement(resultMap.name());
        element.setAttribute("id", resultMap.getId());
        element.setAttribute("type", resultMap.getType().getCanonicalName());

        resultMap.stream()
                .map(it -> {
                    switch (it.type()) {
                        case RESULT:
                        case ID_RESULT:
                            return buildResultElement(document, (Result) it);
                        case ASSOCIATION:
                            return buildAssociationElement(document, (Association) it);
                        default:
                            throw new IllegalArgumentException("");
                    }
                })
                .forEach(element::appendChild);

        return element;
    }

    private Element buildResultElement(Document document, Result result) {
        final Element element = document.createElement(result.name());
        element.setAttribute("property", result.getProperty());
        element.setAttribute("column", result.getColumn());
        if (Assert.nonNull(result.getJavaType())) {
            element.setAttribute("javaType", result.getJavaType().getCanonicalName());
        }
        if (Assert.nonNull(result.getTypeHandler())) {
            element.setAttribute("typeHandler", result.getTypeHandler().getClass().getCanonicalName());
        }
        return element;
    }

    private Element buildAssociationElement(Document document, Association association) {
        final Element element = document.createElement(association.name());
        element.setAttribute("property", association.getProperty());
        element.setAttribute("javaType", association.getJavaType().getCanonicalName());

        association.stream()
                .map(it -> {
                    switch (it.type()) {
                        case RESULT:
                        case ID_RESULT:
                            return buildResultElement(document, (Result) it);
                        default:
                            throw new IllegalArgumentException("");
                    }
                })
                .forEach(element::appendChild);

        return element;
    }


}
