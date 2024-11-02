/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.query;

import lombok.Getter;

/**
 * CriterionExpression.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public final class CriterionExpression {

    private CriterionExpression() {
    }

    private static final String CLOSE_IF = "\">";

    private static final String OPEN_IF = "<if test=\"";

    private static final String END_IF = "</if>";

    private static final String CDATA_OPEN = "<![CDATA[ ";

    private static final String CDATA_CLOSE = " ]]>";

    // =================================================================================================================
    // ===========================================           TEST            ===========================================
    // =================================================================================================================

    /**
     * {@code value != null}
     */
    public static final String TEST_VALUE = "${value} != null";

    /**
     * {@code value != null and value != ''}
     */
    public static final String TEST_LIKE = "${value} != null and ${value} != ''";

    /**
     * {@code value != null and value.size() > 0}
     */
    public static final String TEST_COLLECTION = "${value} != null and ${value}.size() > 0";

    /**
     * {@code value != null and value.min != null and value.max != null}
      */
    public static final String TEST_BETWEEN = "${value} != null and ${value}.min != null and ${value}.max != null";

    // =================================================================================================================
    // ===========================================           VALUE           ===========================================
    // =================================================================================================================

    /**
     * <pre class="code">
     * javaType=${javaType}
     * </pre>
     */
    public static final String JAVA_TYPE = "#if($javaType), javaType=$!{javaType.canonicalName}#end";

    /**
     * <pre class="code">
     * typeHandler=${typeHandler}
     * </pre>
     */
    public static final String TYPE_HANDLER = "#if($typeHandler), typeHandler=$!{typeHandler.canonicalName}#end";

    /**
     * {@code #{${value},javaType=,typeHandler=}}
     */
    public static final String VALUE = "#{${value}" + JAVA_TYPE + TYPE_HANDLER + "}";

    /**
     * {@code #{item,javaType=,typeHandler=}}
     */
    public static final String ITEM = "#{item" + JAVA_TYPE + TYPE_HANDLER + "}";

    /**
     * <pre class="code">
     * #{value,javaType=,typeHandler=}
     * </pre>
     */
    public static final String FRAGMENT_CRITERION_VALUE = "#{${value}.value" + JAVA_TYPE + TYPE_HANDLER + "}";

    /**
     * {@code #{${value}.min,javaType=,typeHandler=}}
     */
    public static final String MIN_VALUE = "#{${value}.min" + JAVA_TYPE + TYPE_HANDLER + "}";

    /**
     * {@code #{${value}.max,javaType=,typeHandler=}}
     */
    public static final String MAX_VALUE = "#{${value}.max" + JAVA_TYPE + TYPE_HANDLER + "}";

    // =================================================================================================================
    // ===========================================           NULL            ===========================================
    // =================================================================================================================

    /**
     * <pre class="code">
     * ${andOr} ${column} IS NULL
     * </pre>
     */
    // NULL
    public static final String IS_NULL = CDATA_OPEN + "${andOr} ${column} IS NULL" + CDATA_CLOSE;

    /**
     * <pre class="code">
     *     ${andOr} ${column} IS NOT NULL
     * </pre>
     */
    public static final String IS_NOT_NULL = CDATA_OPEN + "${andOr} ${column} IS NOT NULL" + CDATA_CLOSE;

    // COMPARE

    // =================================================================================================================
    // ===========================================          COMPARE          ===========================================
    // =================================================================================================================

    /**
     * <pre class="code">
     * &lt;if test="${value} != null"&gt;
     *      ${andOr} ${column} = #{value,javaType=,typeHandler=}
     * &lt;/if&gt;
     * </pre>
     */
    public static final String EQUAL = OPEN_IF + TEST_VALUE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} = " + VALUE
        + CDATA_CLOSE
        + END_IF;

    /**
     * <pre class="code">
     * &lt;if test="${value} != null"&gt;
     *     ${andOr} ${column} != #{value,javaType=,typeHandler=}
     * &lt;/if&gt;
     * </pre>
     */
    public static final String NOT_EQUAL = OPEN_IF + TEST_VALUE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} != " + VALUE
        + CDATA_CLOSE
        + END_IF;

    /**
     * {@code <if test="${value} != null"> ${andOr}
     * </if>
     * }
     */
    public static final String GREAT_THAN = OPEN_IF + TEST_VALUE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} > " + VALUE
        + CDATA_CLOSE
        + END_IF;

    public static final String GREAT_THAN_EQUAL = OPEN_IF + TEST_VALUE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} >= " + VALUE
        + CDATA_CLOSE
        + END_IF;

    public static final String LESS_THAN = OPEN_IF + TEST_VALUE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} < " + VALUE
        + CDATA_CLOSE
        + END_IF;

    public static final String LESS_THAN_EQUAL = OPEN_IF + TEST_VALUE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} <= " + VALUE
        + CDATA_CLOSE
        + END_IF;

    // BETWEEN

    public static final String BETWEEN = OPEN_IF + TEST_BETWEEN + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} BETWEEN " + MIN_VALUE + " AND " + MAX_VALUE
        + CDATA_CLOSE
        + END_IF;

    public static final String NOT_BETWEEN = OPEN_IF + TEST_BETWEEN + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} NOT BETWEEN " + MIN_VALUE + " AND " + MAX_VALUE
        + CDATA_CLOSE
        + END_IF;

    // =================================================================================================================
    // ===========================================           LIKE            ===========================================
    // =================================================================================================================

    // LIKE

    /**
     * <pre class="code">
     * #{column} LIKE #{value}
     * </pre>
     */
    public static final String LIKE = OPEN_IF + TEST_LIKE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} LIKE #{${value}}"
        + CDATA_CLOSE
        + END_IF;

    /**
     * <pre class="code">
     * #{column} NOT LIKE #{value}
     * </pre>
     */
    public static final String NOT_LIKE = OPEN_IF + TEST_LIKE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} NOT LIKE #{${value}}"
        + CDATA_CLOSE
        + END_IF;

    /**
     * <pre class="code">
     * #{column} LIKE CONCAT(#{value},'%')
     * </pre>
     */
    public static final String STARTS_WITH = OPEN_IF + TEST_LIKE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} LIKE CONCAT(#{${value}},'%')"
        + CDATA_CLOSE
        + END_IF;

    /**
     * <pre class="code">
     * #{column} NOT LIKE CONCAT(#{value},'%')
     * </pre>
     */
    public static final String NOT_STARTS_WITH = OPEN_IF + TEST_LIKE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} NOT LIKE CONCAT(#{${value}},'%')"
        + CDATA_CLOSE
        + END_IF;


    /**
     * <pre class="code">
     * #{column} LIKE CONCAT('%',#{value})
     * </pre>
     */
    public static final String ENDS_WITH = OPEN_IF + TEST_LIKE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} LIKE CONCAT('%',#{${value}})"
        + CDATA_CLOSE
        + END_IF;

    /**
     * <pre class="code">
     * #{column} NOT LIKE CONCAT('%',#{value})
     * </pre>
     */
    public static final String NOT_ENDS_WITH = OPEN_IF + TEST_LIKE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} NOT LIKE CONCAT('%',#{${value}})"
        + CDATA_CLOSE
        + END_IF;

    /**
     * <pre class="code">
     * #{column} LIKE CONCAT('%',#{value},'%')
     * </pre>
     */
    public static final String CONTAINS = OPEN_IF + TEST_LIKE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} LIKE CONCAT('%',#{${value}},'%')"
        + CDATA_CLOSE
        + END_IF;

    /**
     * <pre class="code">
     * #{column} NOT LIKE CONCAT('%',#{value},'%')
     * </pre>
     */
    public static final String NOT_CONTAINS = OPEN_IF + TEST_LIKE + CLOSE_IF
        + CDATA_OPEN
        + "${andOr} ${column} NOT LIKE CONCAT('%',#{${value}},'%')"
        + CDATA_CLOSE
        + END_IF;

    // IN

    // =================================================================================================================
    // ===========================================             IN            ===========================================
    // =================================================================================================================
    /**
     * <pre class="code">
     * ${column} IN (values)
     * </pre>
     */
    public static final String IN = OPEN_IF + TEST_COLLECTION + CLOSE_IF
        + "<foreach collection=\"${value}\" item=\"item\" open=\"${andOr} ${column} IN (\" close=\")\" separator=\",\">"
        + ITEM
        + "</foreach>"
        + END_IF;

    /**
     * <pre class="code">
     * ${column} NOT IN (values)
     * </pre>
     */
    public static final String NOT_IN = OPEN_IF + TEST_COLLECTION + CLOSE_IF
        + "<foreach collection=\"${value}\" item=\"item\" open=\"${andOr} ${column} NOT IN (\" close=\")\" separator=\",\">"
        + ITEM
        + "</foreach>"
        + END_IF;

    // JSON

    // =================================================================================================================
    // ===========================================            JSON           ===========================================
    // =================================================================================================================

    /**
     * {@code JSON_CONTAINS(column,value[,path])}
     */
    public static final String JSON_CONTAINS = OPEN_IF + TEST_VALUE + CLOSE_IF
        + "${andOr} JSON_CONTAINS(${column}," + VALUE + "#if($path), '${path}'#end )"
        + END_IF;

    public static final String JSON_ARRAY_CONTAINS = OPEN_IF + TEST_VALUE + CLOSE_IF
            + "${andOr} JSON_CONTAINS( ${column}, JSON_ARRAY(" + VALUE + ")#if($path), '${path}'#end )"
            + END_IF;

    /**
     * {@code !JSON_CONTAINS(column,value[,path])}
     */
    public static final String NOT_JSON_CONTAINS = OPEN_IF + TEST_VALUE + CLOSE_IF
        + "${andOr} !JSON_CONTAINS( ${column}," + VALUE + "#if($path), '${path}'#end )"
        + END_IF;

    public static final String NOT_JSON_ARRAY_CONTAINS = OPEN_IF + TEST_VALUE + CLOSE_IF
            + "${andOr} !JSON_CONTAINS( ${column}, JSON_ARRAY(" + VALUE + ")#if($path), '${path}'#end )"
            + END_IF;

    /**
     *{@code !JSON_CONTAINS(column,one|all,paths)}
     */
    public static final String JSON_CONTAINS_PATH = OPEN_IF + TEST_VALUE + CLOSE_IF
        + "${andOr} JSON_CONTAINS_PATH( ${column}, '${oneOrAll}', <foreach collection=\"${value}\" item=\"item\" separator=\",\">#{item}</foreach>)"
        + END_IF;

    /**
     * {@code !JSON_CONTAINS(column,one|all,paths)}
     */
    public static final String NOT_JSON_CONTAINS_PATH = OPEN_IF + TEST_VALUE + CLOSE_IF
        + "${andOr} !JSON_CONTAINS_PATH( ${column}, '${oneOrAll}', <foreach collection=\"${value}\" item=\"item\" separator=\",\">#{item}</foreach>)"
        + END_IF;

    // =================================================================================================================
    // ===========================================           UPDATE          ===========================================
    // =================================================================================================================

    /**
     * <pre class="code">
     * <if test="${value} != null">
     *     ${column} = #{value,javaType=,typeHandler},
     * </if>
     * </pre>
     */
    public static final String UPDATE_SET = OPEN_IF + TEST_VALUE + CLOSE_IF
        + CDATA_OPEN
        + "${column} = " + FRAGMENT_CRITERION_VALUE + ","
        + CDATA_CLOSE
        + END_IF;

    /**
     * <pre class="code">
     * <if test="${value} != null">
     *     ${column} = ${column} + #{value,javaType=,typeHandler}
     * </if>
     * </pre>
     */
    public static final String UPDATE_INCR = OPEN_IF + TEST_VALUE + CLOSE_IF
        + CDATA_OPEN
        + "${column} = ${column} + " + FRAGMENT_CRITERION_VALUE + ","
        + CDATA_CLOSE
        + END_IF;

    /**
     * <pre class="code">
     * &lt;if test="${value} != null"&gt;
     *      ${column} = ${column} - #{value,javaType=,typeHandler=}
     * &lt;/if&gt;
     * </pre>
     */
    public static final String UPDATE_DECR = OPEN_IF + TEST_VALUE + CLOSE_IF
        + CDATA_OPEN
        + "${column} = ${column} - " + FRAGMENT_CRITERION_VALUE + ","
        + CDATA_CLOSE
        + END_IF;

    /**
     * {@code column = JSON_INSERT(column, path,val[,path,val]...))}
     *
     * @see #JSON_REPLACE
     * @see #JSON_SET
     */
    public static final String JSON_INSERT = OPEN_IF + TEST_VALUE + CLOSE_IF
        + "${column} = JSON_INSERT(${column},<foreach collection=\"${value}.entrySet()\" index=\"key\" item=\"val\" separator=\",\">#{key},#{val}</foreach>),"
        + END_IF;

    /**
     * {@code column = JSON_REPLACE(column, path,val[,path,val]...))}
     *
     * @see #JSON_INSERT
     * @see #JSON_SET
     */
    public static final String JSON_REPLACE = OPEN_IF + TEST_VALUE + CLOSE_IF
        + "${column} = JSON_REPLACE(${column},<foreach collection=\"${value}.value.entrySet()\" index=\"key\" item=\"val\" separator=\",\">#{key},#{val}</foreach>),"
        + END_IF;

    /**
     * {@code column = JSON_SET(column, path,val[,path,val]...))}
     *
     * @see #JSON_INSERT
     * @see #JSON_REPLACE
     */
    public static final String JSON_SET = OPEN_IF + TEST_VALUE + CLOSE_IF
        + "${column} = JSON_SET(${column},<foreach collection=\"${value}.value.entrySet()\" index=\"key\" item=\"val\" separator=\",\">#{key},#{val}</foreach>),"
        + END_IF;

    /**
     * {@code column = JSON_REMOVE(column, path[,path...])}
     */
    public static final String JSON_REMOVE = OPEN_IF + TEST_VALUE + CLOSE_IF
        + "${column} = JSON_REMOVE(${column},<foreach collection=\"${value}.value\" item=\"item\" separator=\",\">#{item}</foreach>),"
        + END_IF;

}
