DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id            BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name          VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age           INT(11)     NULL DEFAULT NULL COMMENT '年龄',
    last_modified DATETIME    NULL DEFAULT NULL ON UPDATE NOW() COMMENT '更新时间',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS person;

CREATE TABLE person
(
    id           BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '流水号',
    name         VARCHAR(30) NOT NULL COMMENT 'name',
    age          INT(11)     NULL DEFAULT NULL COMMENT '年龄',
    creator      BIGINT(20)  NULL DEFAULT NULL COMMENT 'creator',
    creator_name VARCHAR(30) NOT NULL COMMENT 'creator_name',
    PRIMARY KEY (id)
)