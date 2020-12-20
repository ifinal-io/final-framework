DROP TABLE IF EXISTS project;

CREATE TABLE project
(
    id                 BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name               VARCHAR(30)  NOT NULL COMMENT '名称',
    code               VARCHAR(50)  NOT NULL COMMENT 'code',
    description        VARCHAR(255) NOT NULL COMMENT '描述',
    version            INT(11)      NOT NULL DEFAULT 1 COMMENT 'version',
    creator            BIGINT(20)   NOT NULL COMMENT '创建人',
    creator_name       VARCHAR(50)  NOT NULL COMMENT '创建人名称',
    created            DATETIME     NOT NULL DEFAULT NOW() COMMENT '创建时间',
    last_modifier      BIGINT(11)   NULL     DEFAULT NULL COMMENT '最后修改人',
    last_modifier_name VARCHAR(50)  NULL     DEFAULT NULL COMMENT '最后修改人名称',
    last_modified      DATETIME     NULL     DEFAULT NULL ON UPDATE NOW() COMMENT '最后修改时间',
    yn                 TINYINT      NOT NULL DEFAULT 1 COMMENT '有效标记，1：有效，0：无效',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS application;

CREATE TABLE application
(
    id                 BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    project            BIGINT(20)   NOT NULL COMMENT 'project',
    name               VARCHAR(30)  NOT NULL COMMENT '名称',
    code               VARCHAR(50)  NOT NULL COMMENT 'code',
    description        VARCHAR(255) NOT NULL COMMENT '描述',
    version            INT(11)      NOT NULL DEFAULT 1 COMMENT 'version',
    creator            BIGINT(20)   NOT NULL COMMENT '创建人',
    creator_name       VARCHAR(50)  NOT NULL COMMENT '创建人名称',
    created            DATETIME     NOT NULL DEFAULT NOW() COMMENT '创建时间',
    last_modifier      BIGINT(11)   NULL     DEFAULT NULL COMMENT '最后修改人',
    last_modifier_name VARCHAR(50)  NULL     DEFAULT NULL COMMENT '最后修改人名称',
    last_modified      DATETIME     NULL     DEFAULT NULL ON UPDATE NOW() COMMENT '最后修改时间',
    yn                 TINYINT      NOT NULL DEFAULT 1 COMMENT '有效标记，1：有效，0：无效',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS data_source;

CREATE TABLE data_source
(
    id                 BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name               VARCHAR(30)  NOT NULL DEFAULT 'default' COMMENT '名称',
    host               VARCHAR(50)  NOT NULL COMMENT 'host',
    port               INT(11)      NOT NULL DEFAULT 1 COMMENT 'port',
    username           VARCHAR(50)  NOT NULL COMMENT 'username',
    password           VARCHAR(50)  NOT NULL COMMENT 'password',
    `schema`           VARCHAR(50)  NOT NULL COMMENT 'schema',
    driver             VARCHAR(50)  NOT NULL COMMENT 'driver',
    props              JSON         NULL     DEFAULT NULL COMMENT 'props',
    description        VARCHAR(255) NOT NULL COMMENT '描述',
    version            INT(11)      NOT NULL DEFAULT 1 COMMENT 'version',
    creator            BIGINT(20)   NOT NULL COMMENT '创建人',
    creator_name       VARCHAR(50)  NOT NULL COMMENT '创建人名称',
    created            DATETIME     NOT NULL DEFAULT NOW() COMMENT '创建时间',
    last_modifier      BIGINT(11)   NULL     DEFAULT NULL COMMENT '最后修改人',
    last_modifier_name VARCHAR(50)  NULL     DEFAULT NULL COMMENT '最后修改人名称',
    last_modified      DATETIME     NULL     DEFAULT NULL ON UPDATE NOW() COMMENT '最后修改时间',
    yn                 TINYINT      NOT NULL DEFAULT 1 COMMENT '有效标记，1：有效，0：无效',
    PRIMARY KEY (id)
);

