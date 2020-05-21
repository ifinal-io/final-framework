---
layout: post
title: example
subtitle: example
categories: []
tags: []
menus: [mapper,example]
author: likly
date: 2020-05-21 14:25:37 +800
version: 1.0
---

# Example

## Define Entity

```java
@Data
public class Person extends AbsRecord {
    private static final long serialVersionUID = -8785625823175210092L;
    //    @PrimaryKey(insertable = true)
//    private Long id;
    @JsonView(Person.class)
    @NotNull
    @View(Person.class)
    private String name;
    @Sharding
    @JsonView(Person.class)
    private Integer age;
    @ReadOnly
    @Function(reader = "MAX(age)")
    @View(Person.class)
    private Integer maxAge;
    @View(Person.class)
    @Json
    private List<String> stringList;
    @Column
    private List<Integer> intList;
    @Json
    private Map<String, Object> properties;
}

```
## Compile

```shell
mvn clean compile
```



## Generated Query

```java
@Generated("org.finalframework.coding.query.QEntityGenerator")
public final class QPerson extends AbsQEntity<Long,Person>{

    public QPerson() {
        super(Person.class);
    }

    public QPerson(String table) {
        super(Person.class, table);
    }

    public static final QPerson Person = new QPerson();

    // path = id, name = id, column = id
    public static final QProperty<Long> id = Person.getRequiredProperty("id");
    // path = name, name = name, column = name
    public static final QProperty<String> name = Person.getRequiredProperty("name");
    // path = age, name = age, column = age
    public static final QProperty<Integer> age = Person.getRequiredProperty("age");
    // path = maxAge, name = maxAge, column = max_age
    public static final QProperty<Integer> maxAge = Person.getRequiredProperty("maxAge");
    // path = stringList, name = stringList, column = string_list
    public static final QProperty<String> stringList = Person.getRequiredProperty("stringList");
    // path = intList, name = intList, column = int_list
    public static final QProperty<Integer> intList = Person.getRequiredProperty("intList");
    // path = properties, name = properties, column = properties
    public static final QProperty<java.util.Map> properties = Person.getRequiredProperty("properties");
    // path = creator.id, name = creator, column = creator
    public static final QProperty<Long> creator = Person.getRequiredProperty("creator.id");
    // path = creator.name, name = creatorName, column = creator_name
    public static final QProperty<String> creatorName = Person.getRequiredProperty("creator.name");
    // path = lastModifier.id, name = lastModifier, column = last_modifier
    public static final QProperty<Long> lastModifier = Person.getRequiredProperty("lastModifier.id");
    // path = lastModifier.name, name = lastModifierName, column = last_modifier_name
    public static final QProperty<String> lastModifierName = Person.getRequiredProperty("lastModifier.name");
    // path = version, name = version, column = version
    public static final QProperty<Long> version = Person.getRequiredProperty("version");
    // path = created, name = created, column = created
    public static final QProperty<java.time.LocalDateTime> created = Person.getRequiredProperty("created");
    // path = lastModified, name = lastModified, column = last_modified
    public static final QProperty<java.time.LocalDateTime> lastModified = Person.getRequiredProperty("lastModified");
    // path = yn, name = yn, column = yn
    public static final QProperty<org.finalframework.data.entity.enums.YN> yn = Person.getRequiredProperty("yn");
}

```

## Generated Mapper.java

```java
@Mapper
@Generated("org.finalframework.coding.mapper.MapperGenerator")
public interface PersonMapper extends AbsMapper<Long,Person>{

}

```
## Generated Mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.finalframework.test.dao.mapper.PersonMapper">

    <sql id="test">
        SELECT
        <foreach collection="update" item="item" separator=",">

        </foreach>
    </sql>

	<!--==============================================================================================================-->
	<!--=====START===============================GENERATED-BY-FINAL-FRAMEWORK===============================START=====-->
	<!--==============================================================================================================-->
	<resultMap id="PersonMap" type="org.finalframework.test.entity.Person">
        <id column="id" javaType="java.lang.Long" property="id"/>
        <result column="name" javaType="java.lang.String" property="name"/>
        <result column="age" javaType="java.lang.Integer" property="age"/>
        <result column="max_age" javaType="java.lang.Integer" property="maxAge"/>
        <result column="string_list" javaType="java.lang.String" property="stringList" typeHandler="org.finalframework.mybatis.handler.JsonListTypeHandler"/>
        <result column="int_list" javaType="java.lang.Integer" property="intList" typeHandler="org.finalframework.mybatis.handler.JsonListTypeHandler"/>
        <result column="properties" javaType="java.util.Map" property="properties" typeHandler="org.finalframework.mybatis.handler.JsonObjectTypeHandler"/>
        <result column="version" javaType="java.lang.Long" property="version"/>
        <result column="created" javaType="java.time.LocalDateTime" property="created" typeHandler="org.finalframework.mybatis.handler.sharing.LocalDateTimeTypeHandler"/>
        <result column="last_modified" javaType="java.time.LocalDateTime" property="lastModified" typeHandler="org.finalframework.mybatis.handler.sharing.LocalDateTimeTypeHandler"/>
        <result column="yn" javaType="org.finalframework.data.entity.enums.YN" property="yn"/>
        <association javaType="org.finalframework.data.entity.AbsUser" property="creator">
            <id column="creator" javaType="java.lang.Long" property="id"/>
            <result column="creator_name" javaType="java.lang.String" property="name"/>
        </association>
        <association javaType="org.finalframework.data.entity.AbsUser" property="lastModifier">
            <id column="last_modifier" javaType="java.lang.Long" property="id"/>
            <result column="last_modifier_name" javaType="java.lang.String" property="name"/>
        </association>
    </resultMap>
    <!--==============================================================================================================-->
    <!--=====INSERT==============================GENERATED-BY-FINAL-FRAMEWORK==============================INSERT=====-->
    <!--==============================================================================================================-->
    <insert id="insert" keyColumn="id" keyProperty="list.id" useGeneratedKeys="true">
        <trim prefix="INSERT">
            <choose>
                <when test="ignore == true"> IGNORE INTO</when>
                <otherwise>INTO</otherwise>
            </choose>
            <include refid="sql-tables"/>
        </trim>
        <choose>
            <when test="view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()">()</when>
            <when test="view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()">(name,string_list)</when>
            <when test="view == null">(name,age,string_list,int_list,properties,creator,creator_name,last_modifier,last_modifier_name)</when>
        </choose>
        <trim prefix="VALUES">
            <foreach collection="list" index="index" item="entity" separator=",">
                <bind name="name" value="entity.name"/>
                <bind name="age" value="entity.age"/>
                <bind name="stringList" value="entity.stringList"/>
                <bind name="intList" value="entity.intList"/>
                <bind name="properties" value="entity.properties"/>
                <bind name="creator" value="entity.creator != null ? entity.creator.id : null"/>
                <bind name="creatorName" value="entity.creator != null ? entity.creator.name : null"/>
                <bind name="lastModifier" value="entity.lastModifier != null ? entity.lastModifier.id : null"/>
                <bind name="lastModifierName" value="entity.lastModifier != null ? entity.lastModifier.name : null"/>
                <trim prefix="(" suffix=")" suffixOverrides=",">
                    <choose>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()"/>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()">#{name,javaType=java.lang.String},#{stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},</when>
                        <when test="view == null">#{name,javaType=java.lang.String},#{age,javaType=java.lang.Integer},#{stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},#{intList,javaType=java.lang.Integer,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},#{properties,javaType=java.util.Map,typeHandler=org.finalframework.mybatis.handler.JsonObjectTypeHandler},#{creator,javaType=java.lang.Long},#{creatorName,javaType=java.lang.String},#{lastModifier,javaType=java.lang.Long},#{lastModifierName,javaType=java.lang.String},</when>
                    </choose>
                </trim>
            </foreach>
        </trim>
    </insert>
    <!--==============================================================================================================-->
    <!--=====REPLACE=============================GENERATED-BY-FINAL-FRAMEWORK=============================REPLACE=====-->
    <!--==============================================================================================================-->
    <insert id="replace" keyColumn="id" keyProperty="list.id" useGeneratedKeys="true">
        <trim prefix="REPLACE INTO">
            <include refid="sql-tables"/>
        </trim>
        <choose>
            <when test="view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()">()</when>
            <when test="view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()">(name,string_list)</when>
            <when test="view == null">(name,age,string_list,int_list,properties,creator,creator_name,last_modifier,last_modifier_name)</when>
        </choose>
        <trim prefix="VALUES">
            <foreach collection="list" index="index" item="entity" separator=",">
                <bind name="name" value="entity.name"/>
                <bind name="age" value="entity.age"/>
                <bind name="stringList" value="entity.stringList"/>
                <bind name="intList" value="entity.intList"/>
                <bind name="properties" value="entity.properties"/>
                <bind name="creator" value="entity.creator != null ? entity.creator.id : null"/>
                <bind name="creatorName" value="entity.creator != null ? entity.creator.name : null"/>
                <bind name="lastModifier" value="entity.lastModifier != null ? entity.lastModifier.id : null"/>
                <bind name="lastModifierName" value="entity.lastModifier != null ? entity.lastModifier.name : null"/>
                <trim prefix="(" suffix=")" suffixOverrides=",">
                    <choose>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()"/>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()">#{name,javaType=java.lang.String},#{stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},</when>
                        <when test="view == null">#{name,javaType=java.lang.String},#{age,javaType=java.lang.Integer},#{stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},#{intList,javaType=java.lang.Integer,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},#{properties,javaType=java.util.Map,typeHandler=org.finalframework.mybatis.handler.JsonObjectTypeHandler},#{creator,javaType=java.lang.Long},#{creatorName,javaType=java.lang.String},#{lastModifier,javaType=java.lang.Long},#{lastModifierName,javaType=java.lang.String},</when>
                    </choose>
                </trim>
            </foreach>
        </trim>
    </insert>
    <!--==============================================================================================================-->
    <!--=====SAVE================================GENERATED-BY-FINAL-FRAMEWORK================================SAVE=====-->
    <!--==============================================================================================================-->
    <insert id="save" keyColumn="id" keyProperty="list.id" useGeneratedKeys="true">
        <trim prefix="INSERT INTO">
            <include refid="sql-tables"/>
        </trim>
        <choose>
            <when test="view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()">()</when>
            <when test="view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()">(name,string_list)</when>
            <when test="view == null">(name,age,string_list,int_list,properties,creator,creator_name,last_modifier,last_modifier_name)</when>
        </choose>
        <trim prefix="VALUES">
            <foreach collection="list" index="index" item="entity" separator=",">
                <bind name="name" value="entity.name"/>
                <bind name="age" value="entity.age"/>
                <bind name="stringList" value="entity.stringList"/>
                <bind name="intList" value="entity.intList"/>
                <bind name="properties" value="entity.properties"/>
                <bind name="creator" value="entity.creator != null ? entity.creator.id : null"/>
                <bind name="creatorName" value="entity.creator != null ? entity.creator.name : null"/>
                <bind name="lastModifier" value="entity.lastModifier != null ? entity.lastModifier.id : null"/>
                <bind name="lastModifierName" value="entity.lastModifier != null ? entity.lastModifier.name : null"/>
                <trim prefix="(" suffix=")" suffixOverrides=",">
                    <choose>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()"/>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()">#{name,javaType=java.lang.String},#{stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},</when>
                        <when test="view == null">#{name,javaType=java.lang.String},#{age,javaType=java.lang.Integer},#{stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},#{intList,javaType=java.lang.Integer,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},#{properties,javaType=java.util.Map,typeHandler=org.finalframework.mybatis.handler.JsonObjectTypeHandler},#{creator,javaType=java.lang.Long},#{creatorName,javaType=java.lang.String},#{lastModifier,javaType=java.lang.Long},#{lastModifierName,javaType=java.lang.String},</when>
                    </choose>
                </trim>
            </foreach>
        </trim>
        <include refid="sql-on-duplicate-key"/>
    </insert>
    <!--==============================================================================================================-->
    <!--=====UPDATE==============================GENERATED-BY-FINAL-FRAMEWORK==============================UPDATE=====-->
    <!--==============================================================================================================-->
    <update id="update">
        <trim prefix="UPDATE">
            <include refid="sql-tables"/>
            <include refid="sql-update"/>
            <choose>
                <when test="ids != null">
                    <include refid="sql-where-ids"/>
                </when>
                <when test="query != null">
                    <include refid="sql-query"/>
                </when>
            </choose>
        </trim>
    </update>
    <sql id="sql-update">
        <set>
            <choose>
                <when test="entity != null and selective == true">
                    <choose>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()">
                            <if test="entity.yn != null">yn = #{entity.yn},</if>
                        </when>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()">
                            <if test="entity.name != null">name = #{entity.name},</if>
                            <if test="entity.stringList != null">string_list = #{entity.stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},</if>
                        </when>
                        <when test="view == null">
                            <if test="entity.name != null">name = #{entity.name},</if>
                            <if test="entity.age != null">age = #{entity.age},</if>
                            <if test="entity.stringList != null">string_list = #{entity.stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},</if>
                            <if test="entity.intList != null">int_list = #{entity.intList,javaType=java.lang.Integer,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},</if>
                            <if test="entity.properties != null">properties = #{entity.properties,javaType=java.util.Map,typeHandler=org.finalframework.mybatis.handler.JsonObjectTypeHandler},</if>
                            <if test="entity.creator != null and entity.creator.id != null">creator = #{entity.creator.id},</if>
                            <if test="entity.creator != null and entity.creator.name != null">creator_name = #{entity.creator.name},</if>
                            <if test="entity.lastModifier != null and entity.lastModifier.id != null">last_modifier = #{entity.lastModifier.id},</if>
                            <if test="entity.lastModifier != null and entity.lastModifier.name != null">last_modifier_name = #{entity.lastModifier.name},</if>
                            <if test="entity.yn != null">yn = #{entity.yn},</if>
                        </when>
                    </choose>
                </when>
                <when test="entity != null">
                    <choose>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()">yn = #{entity.yn},</when>
                        <when test="view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()">name = #{entity.name},string_list = #{entity.stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},</when>
                        <when test="view == null">name = #{entity.name},age = #{entity.age},string_list = #{entity.stringList,javaType=java.lang.String,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},int_list = #{entity.intList,javaType=java.lang.Integer,typeHandler=org.finalframework.mybatis.handler.JsonListTypeHandler},properties = #{entity.properties,javaType=java.util.Map,typeHandler=org.finalframework.mybatis.handler.JsonObjectTypeHandler},<if test="entity.creator != null">creator = #{entity.creator.id},</if>
                            <if test="entity.creator != null">creator_name = #{entity.creator.name},</if>
                            <if test="entity.lastModifier != null">last_modifier = #{entity.lastModifier.id},</if>
                            <if test="entity.lastModifier != null">last_modifier_name = #{entity.lastModifier.name},</if>yn = #{entity.yn},</when>
                    </choose>
                </when>
                <when test="update != null">
                    <foreach collection="update" item="item" separator=",">
                        <choose>
                            <when test="item.operation.name() == 'EQUAL'">${item.updateTarget} = ${item.updateValue}</when>
                            <when test="item.operation.name() == 'INC'">${item.updateTarget} = ${item.updateTarget} + 1</when>
                            <when test="item.operation.name() == 'INCR'">${item.updateTarget} = ${item.updateTarget} + ${item.updateValue}</when>
                            <when test="item.operation.name() == 'DEC'">${item.updateTarget} = ${item.updateTarget} - 1</when>
                            <when test="item.operation.name() == 'DECR'">${item.updateTarget} = ${item.updateTarget} - ${item.updateValue}</when>
                        </choose>
                    </foreach>
                </when>
            </choose>
        </set>
    </sql>
    <!--==============================================================================================================-->
    <!--=====DELETE==============================GENERATED-BY-FINAL-FRAMEWORK==============================DELETE=====-->
    <!--==============================================================================================================-->
    <delete id="delete">
        <trim prefix="DELETE FROM">
            <include refid="sql-tables"/>
            <choose>
                <when test="ids != null">
                    <include refid="sql-where-ids"/>
                </when>
                <when test="query != null">
                    <include refid="sql-query"/>
                </when>
            </choose>
        </trim>
    </delete>
    <!--==============================================================================================================-->
    <!--=====SELECT==============================GENERATED-BY-FINAL-FRAMEWORK==============================SELECT=====-->
    <!--==============================================================================================================-->
    <select id="select" resultType="org.finalframework.test.entity.Person">
        <trim prefix="SELECT">
            <include refid="sql-select-columns"/>
        </trim>
        <trim prefix="FROM">
            <include refid="sql-tables"/>
        </trim>
        <choose>
            <when test="ids != null">
                <include refid="sql-where-ids"/>
            </when>
            <when test="query != null">
                <include refid="sql-query"/>
            </when>
        </choose>
    </select>
    <!--==============================================================================================================-->
    <!--=====SELECT=ONE==========================GENERATED-BY-FINAL-FRAMEWORK==========================SELECT=ONE=====-->
    <!--==============================================================================================================-->
    <select id="selectOne" resultType="org.finalframework.test.entity.Person">
        <trim prefix="SELECT">
            <include refid="sql-select-columns"/>
        </trim>
        <trim prefix="FROM">
            <include refid="sql-tables"/>
        </trim>
        <choose>
            <when test="id != null">
                <include refid="sql-where-id"/>
            </when>
            <when test="query != null">
                <include refid="sql-query"/>
            </when>
        </choose>
    </select>
    <!--==============================================================================================================-->
    <!--=====SELECT=IDS==========================GENERATED-BY-FINAL-FRAMEWORK==========================SELECT=IDS=====-->
    <!--==============================================================================================================-->
    <select id="selectIds" resultType="java.lang.Long">
        <trim prefix="SELECT id FROM">
            <include refid="sql-tables"/>
            <include refid="sql-query"/>
        </trim>
    </select>
    <!--==============================================================================================================-->
    <!--=====SELECT=COUNT========================GENERATED-BY-FINAL-FRAMEWORK========================SELECT=COUNT=====-->
    <!--==============================================================================================================-->
    <select id="selectCount" resultType="java.lang.Long">
        <trim prefix="SELECT COUNT(*) FROM">
            <include refid="sql-tables"/>
            <choose>
                <when test="ids != null">
                    <include refid="sql-where-ids"/>
                </when>
                <when test="query != null">
                    <include refid="sql-query"/>
                </when>
            </choose>
        </trim>
    </select>
    <!--==============================================================================================================-->
    <!--=====TRUNCATE============================GENERATED-BY-FINAL-FRAMEWORK============================TRUNCATE=====-->
    <!--==============================================================================================================-->
    <update id="truncate">
        <trim prefix="TRUNCATE TABLE">
            <include refid="sql-tables"/>
        </trim>
    </update>
    <!--==============================================================================================================-->
    <!--======FRAGMENT===========================GENERATED-BY-FINAL-FRAMEWORK===========================FRAGMENT======-->
    <!--==============================================================================================================-->
    <!--==============================================================================================================-->
    <!--=====SQL-TABLE===========================GENERATED-BY-FINAL-FRAMEWORK===========================SQL-TABLE=====-->
    <!--==============================================================================================================-->
    <sql id="sql-table">person</sql>
    <!--==============================================================================================================-->
    <!--=====SQL-TABLES==========================GENERATED-BY-FINAL-FRAMEWORK==========================SQL-TABLES=====-->
    <!--==============================================================================================================-->
    <sql id="sql-tables">
        <choose>
            <when test="table != null">${table}</when>
            <otherwise>person</otherwise>
        </choose>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-ON-DUPLICATE-KEY================GENERATED-BY-FINAL-FRAMEWORK================SQL-ON-DUPLICATE-KEY=====-->
    <!--==============================================================================================================-->
    <sql id="sql-on-duplicate-key">
        <trim prefix=" ON DUPLICATE KEY UPDATE ">name = values(name),string_list = values(string_list),int_list = values(int_list),properties = values(properties),creator = values(creator),creator_name = values(creator_name),last_modifier = values(last_modifier),last_modifier_name = values(last_modifier_name),id = values(id),version = values(version) + 1,created = values(created),last_modified = NOW(),yn = values(yn)</trim>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-WHERE-ID========================GENERATED-BY-FINAL-FRAMEWORK========================SQL-WHERE-ID=====-->
    <!--==============================================================================================================-->
    <sql id="sql-where-id">
        <where>
            <trim prefix="id =">#{id}</trim>
        </where>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-WHERE-IDS=======================GENERATED-BY-FINAL-FRAMEWORK=======================SQL-WHERE-IDS=====-->
    <!--==============================================================================================================-->
    <sql id="sql-where-ids">
        <where>
            <trim prefix="id IN">
                <foreach close=")" collection="ids" item="id" open="(" separator=",">#{id}</foreach>
            </trim>
        </where>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-SELECT-COLUMNS==================GENERATED-BY-FINAL-FRAMEWORK==================SQL-SELECT-COLUMNS=====-->
    <!--==============================================================================================================-->
    <sql id="sql-select-columns">
        <choose>
            <when test="view != null and view.getCanonicalName() == 'org.finalframework.data.annotation.IView'.toString()">id,version,created,last_modified,yn</when>
            <when test="view != null and view.getCanonicalName() == 'org.finalframework.test.entity.Person'.toString()">name,MAX(age) AS max_age,string_list</when>
            <otherwise>name,age,string_list,int_list,properties,creator,creator_name,last_modifier,last_modifier_name,id,created,yn</otherwise>
        </choose>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-WHERE-CRITERIA==================GENERATED-BY-FINAL-FRAMEWORK==================SQL-WHERE-CRITERIA=====-->
    <!--==============================================================================================================-->
    <sql id="sql-where-criteria">
        <where>
            <foreach collection="criteria" item="criterion" separator=" AND ">
                <choose>
                    <when test="criterion.chain">
                        <include refid="sql-criteria"/>
                    </when>
                    <otherwise>
                        <include refid="sql-criterion"/>
                    </otherwise>
                </choose>
            </foreach>
        </where>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-CRITERIA========================GENERATED-BY-FINAL-FRAMEWORK========================SQL-CRITERIA=====-->
    <!--==============================================================================================================-->
    <sql id="sql-criteria">
        <choose>
            <when test="criterion.andOr.name == 'AND'">
                <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                    <choose>
                        <when test="criterion.chain">
                            <choose>
                                <when test="criterion.andOr.name == 'AND'">
                                    <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                        <choose>
                                            <when test="criterion.chain">
                                                <choose>
                                                    <when test="criterion.andOr.name == 'AND'">
                                                        <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                            <choose>
                                                                <when test="criterion.chain">
                                                                    <choose>
                                                                        <when test="criterion.andOr.name == 'AND'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                        <when test="criterion.andOr.name == 'OR'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                    </choose>
                                                                </when>
                                                                <otherwise>
                                                                    <include refid="sql-criterion"/>
                                                                </otherwise>
                                                            </choose>
                                                        </foreach>
                                                    </when>
                                                    <when test="criterion.andOr.name == 'OR'">
                                                        <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                            <choose>
                                                                <when test="criterion.chain">
                                                                    <choose>
                                                                        <when test="criterion.andOr.name == 'AND'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                        <when test="criterion.andOr.name == 'OR'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                    </choose>
                                                                </when>
                                                                <otherwise>
                                                                    <include refid="sql-criterion"/>
                                                                </otherwise>
                                                            </choose>
                                                        </foreach>
                                                    </when>
                                                </choose>
                                            </when>
                                            <otherwise>
                                                <include refid="sql-criterion"/>
                                            </otherwise>
                                        </choose>
                                    </foreach>
                                </when>
                                <when test="criterion.andOr.name == 'OR'">
                                    <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                        <choose>
                                            <when test="criterion.chain">
                                                <choose>
                                                    <when test="criterion.andOr.name == 'AND'">
                                                        <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                            <choose>
                                                                <when test="criterion.chain">
                                                                    <choose>
                                                                        <when test="criterion.andOr.name == 'AND'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                        <when test="criterion.andOr.name == 'OR'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                    </choose>
                                                                </when>
                                                                <otherwise>
                                                                    <include refid="sql-criterion"/>
                                                                </otherwise>
                                                            </choose>
                                                        </foreach>
                                                    </when>
                                                    <when test="criterion.andOr.name == 'OR'">
                                                        <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                            <choose>
                                                                <when test="criterion.chain">
                                                                    <choose>
                                                                        <when test="criterion.andOr.name == 'AND'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                        <when test="criterion.andOr.name == 'OR'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                    </choose>
                                                                </when>
                                                                <otherwise>
                                                                    <include refid="sql-criterion"/>
                                                                </otherwise>
                                                            </choose>
                                                        </foreach>
                                                    </when>
                                                </choose>
                                            </when>
                                            <otherwise>
                                                <include refid="sql-criterion"/>
                                            </otherwise>
                                        </choose>
                                    </foreach>
                                </when>
                            </choose>
                        </when>
                        <otherwise>
                            <include refid="sql-criterion"/>
                        </otherwise>
                    </choose>
                </foreach>
            </when>
            <when test="criterion.andOr.name == 'OR'">
                <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                    <choose>
                        <when test="criterion.chain">
                            <choose>
                                <when test="criterion.andOr.name == 'AND'">
                                    <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                        <choose>
                                            <when test="criterion.chain">
                                                <choose>
                                                    <when test="criterion.andOr.name == 'AND'">
                                                        <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                            <choose>
                                                                <when test="criterion.chain">
                                                                    <choose>
                                                                        <when test="criterion.andOr.name == 'AND'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                        <when test="criterion.andOr.name == 'OR'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                    </choose>
                                                                </when>
                                                                <otherwise>
                                                                    <include refid="sql-criterion"/>
                                                                </otherwise>
                                                            </choose>
                                                        </foreach>
                                                    </when>
                                                    <when test="criterion.andOr.name == 'OR'">
                                                        <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                            <choose>
                                                                <when test="criterion.chain">
                                                                    <choose>
                                                                        <when test="criterion.andOr.name == 'AND'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                        <when test="criterion.andOr.name == 'OR'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                    </choose>
                                                                </when>
                                                                <otherwise>
                                                                    <include refid="sql-criterion"/>
                                                                </otherwise>
                                                            </choose>
                                                        </foreach>
                                                    </when>
                                                </choose>
                                            </when>
                                            <otherwise>
                                                <include refid="sql-criterion"/>
                                            </otherwise>
                                        </choose>
                                    </foreach>
                                </when>
                                <when test="criterion.andOr.name == 'OR'">
                                    <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                        <choose>
                                            <when test="criterion.chain">
                                                <choose>
                                                    <when test="criterion.andOr.name == 'AND'">
                                                        <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                            <choose>
                                                                <when test="criterion.chain">
                                                                    <choose>
                                                                        <when test="criterion.andOr.name == 'AND'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                        <when test="criterion.andOr.name == 'OR'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                    </choose>
                                                                </when>
                                                                <otherwise>
                                                                    <include refid="sql-criterion"/>
                                                                </otherwise>
                                                            </choose>
                                                        </foreach>
                                                    </when>
                                                    <when test="criterion.andOr.name == 'OR'">
                                                        <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                            <choose>
                                                                <when test="criterion.chain">
                                                                    <choose>
                                                                        <when test="criterion.andOr.name == 'AND'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" AND ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                        <when test="criterion.andOr.name == 'OR'">
                                                                            <foreach close=")" collection="criterion.criteria" item="criterion" open="(" separator=" OR ">
                                                                                <choose>
                                                                                    <when test="criterion.chain"/>
                                                                                    <otherwise>
                                                                                        <include refid="sql-criterion"/>
                                                                                    </otherwise>
                                                                                </choose>
                                                                            </foreach>
                                                                        </when>
                                                                    </choose>
                                                                </when>
                                                                <otherwise>
                                                                    <include refid="sql-criterion"/>
                                                                </otherwise>
                                                            </choose>
                                                        </foreach>
                                                    </when>
                                                </choose>
                                            </when>
                                            <otherwise>
                                                <include refid="sql-criterion"/>
                                            </otherwise>
                                        </choose>
                                    </foreach>
                                </when>
                            </choose>
                        </when>
                        <otherwise>
                            <include refid="sql-criterion"/>
                        </otherwise>
                    </choose>
                </foreach>
            </when>
        </choose>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-CRITERION=======================GENERATED-BY-FINAL-FRAMEWORK=======================SQL-CRITERION=====-->
    <!--==============================================================================================================-->
    <sql id="sql-criterion">
        <choose>
            <when test="'NULL' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} IS NULL]]></when>
            <when test="'NOT_NULL' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} IS NOT NULL]]></when>
            <when test="'EQUAL' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} = ${criterion.criterionValue}]]></when>
            <when test="'NOT_EQUAL' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} != ${criterion.criterionValue}]]></when>
            <when test="'GREAT_THAN' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} > ${criterion.criterionValue}]]></when>
            <when test="'GREAT_THAN_EQUAL' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} >= ${criterion.criterionValue}]]></when>
            <when test="'LESS_THAN' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} < ${criterion.criterionValue}]]></when>
            <when test="'LESS_THAN_EQUAL' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} <= ${criterion.criterionValue}]]></when>
            <when test="'BETWEEN' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} BETWEEN ${criterion.criterionMin} AND ${criterion.criterionMax}]]></when>
            <when test="'NOT_BETWEEN' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} NOT BETWEEN ${criterion.criterionMin} AND ${criterion.criterionMax}]]></when>
            <when test="'LIKE' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} LIKE ${criterion.criterionValue}]]></when>
            <when test="'NOT_LIKE' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} NOT LIKE ${criterion.criterionValue}]]></when>
            <when test="'IN' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} IN]]>
                <foreach close=")" collection="criterion.value" item="value" open="(" separator=",">${criterion.criterionValue}</foreach>
            </when>
            <when test="'NOT_IN' == criterion.operation.name()"><![CDATA[${criterion.criterionTarget} NOT IN]]>
                <foreach close=")" collection="criterion.value" item="value" open="(" separator=",">${criterion.criterionValue}</foreach>
            </when>
        </choose>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-GROUP===========================GENERATED-BY-FINAL-FRAMEWORK===========================SQL-GROUP=====-->
    <!--==============================================================================================================-->
    <sql id="sql-group">
        <if test="group != null">
            <trim prefix="GROUP BY">
                <foreach collection="group" item="property" separator=",">${property.column}</foreach>
            </trim>
        </if>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-ORDER===========================GENERATED-BY-FINAL-FRAMEWORK===========================SQL-ORDER=====-->
    <!--==============================================================================================================-->
    <sql id="sql-order">
        <if test="sort != null">
            <trim prefix="ORDER BY">
                <foreach collection="sort" item="order" separator=",">${order.property.column} ${order.direction.value}</foreach>
            </trim>
        </if>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-LIMIT===========================GENERATED-BY-FINAL-FRAMEWORK===========================SQL-LIMIT=====-->
    <!--==============================================================================================================-->
    <sql id="sql-limit">
        <if test="limit != null">
            <trim prefix="LIMIT">
                <if test="limit.offset != null">#{limit.offset},</if>
                <if test="limit.limit != null">#{limit.limit}</if>
            </trim>
        </if>
    </sql>
    <!--==============================================================================================================-->
    <!--=====SQL-QUERY===========================GENERATED-BY-FINAL-FRAMEWORK===========================SQL-QUERY=====-->
    <!--==============================================================================================================-->
    <sql id="sql-query">
        <if test="query != null">
            <bind name="criteria" value="query.criteria"/>
            <bind name="group" value="query.group"/>
            <bind name="sort" value="query.sort"/>
            <bind name="limit" value="query.limit"/>
            <include refid="sql-where-criteria"/>
            <include refid="sql-group"/>
            <include refid="sql-order"/>
            <include refid="sql-limit"/>
        </if>
    </sql>
    <!--==============================================================================================================-->
    <!--======END================================GENERATED-BY-FINAL-FRAMEWORK================================END======-->
    <!--==============================================================================================================-->
</mapper>

```