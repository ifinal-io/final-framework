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

package org.finalframework.mybatis.sql;


import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-19 19:17:42
 * @since 1.0
 */
public class SaveSqlProvider {
    public String save(Map<String, Object> params) {
        return "<script>\n" +
                "\n" +
                "        <insert id=\"insert\" keyColumn=\"id\" keyProperty=\"list.id\" useGeneratedKeys=\"true\">\n" +
                "            <trim prefix=\"INSERT\">\n" +
                "                <choose>\n" +
                "                    <when test=\"ignore == true\">IGNORE INTO</when>\n" +
                "                    <otherwise>INTO</otherwise>\n" +
                "                </choose>\n" +
                "                <include refid=\"sql-tables\"/>\n" +
                "            </trim>\n" +
                "            <choose>\n" +
                "                <when test=\"view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()\">\n" +
                "                    (name,string_list)\n" +
                "                </when>\n" +
                "                <when test=\"view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()\">\n" +
                "                    ()\n" +
                "                </when>\n" +
                "                <when test=\"view == null\">\n" +
                "                    (name,age,string_list,int_list,creator,creator_name,last_modifier,last_modifier_name)\n" +
                "                </when>\n" +
                "            </choose>\n" +
                "            <trim prefix=\"VALUES\">\n" +
                "                <foreach collection=\"list\" index=\"index\" item=\"entity\" separator=\",\">\n" +
                "                    <bind name=\"name\" value=\"entity.name\"/>\n" +
                "                    <bind name=\"age\" value=\"entity.age\"/>\n" +
                "                    <bind name=\"stringList\" value=\"entity.stringList\"/>\n" +
                "                    <bind name=\"intList\" value=\"entity.intList\"/>\n" +
                "                    <bind name=\"creator\" value=\"entity.creator != null ? entity.creator.id : null\"/>\n" +
                "                    <bind name=\"creatorName\" value=\"entity.creator != null ? entity.creator.name : null\"/>\n" +
                "                    <bind name=\"lastModifier\" value=\"entity.lastModifier != null ? entity.lastModifier.id : null\"/>\n" +
                "                    <bind name=\"lastModifierName\"\n" +
                "                          value=\"entity.lastModifier != null ? entity.lastModifier.name : null\"/>\n" +
                "                    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
                "                        <choose>\n" +
                "                            <when test=\"view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()\">\n" +
                "                                #{name,javaType=java.lang.String},#{stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},\n" +
                "                            </when>\n" +
                "                            <when test=\"view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()\"/>\n" +
                "                            <when test=\"view == null\">#{name,javaType=java.lang.String},\n" +
                "                                #{age,javaType=java.lang.Integer},\n" +
                "                                #{stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},\n" +
                "                                #{intList,javaType=java.lang.Integer,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},\n" +
                "                                #{creator,javaType=java.lang.Long},\n" +
                "                                #{creatorName,javaType=java.lang.String},\n" +
                "                                #{lastModifier,javaType=java.lang.Long},\n" +
                "                                <bind name=\"lastModifierName\"\n" +
                "                                      value=\"entity.lastModifier != null ? entity.lastModifier.name : null\">\n" +
                "                                    #{lastModifierName},\n" +
                "                                </bind>\n" +
                "                            </when>\n" +
                "                        </choose>\n" +
                "                    </trim>\n" +
                "                </foreach>\n" +
                "            </trim>\n" +
                "        </insert>\n" +
                "    </script>";
    }
}

