#电子书表
drop table if exists `ebook`;
create table `ebook`(
    `id` varchar(200) not null comment 'id',
    `name` varchar(50) comment '名称',
    `category1_id` bigint comment '分类1',
    `category2_id` bigint comment '分类2',
    `description` varchar(200) comment '描述',
    `cover` varchar(200) comment '蜂蜜',
    `doc_count` int comment '文档数',
    `view_count` int comment '阅读数',
    `vote_count` int comment '点击数',
    primary key (`id`)
)engine=innodb default charset=utf8mb4 comment '电子书';

-- 分类
create table `category`(
    `id` varchar(50) not null comment 'id',
    `parent` varchar(50) not null default '0' comment '父ID',
    `name` varchar(200) not null comment '名称',
    `sort` int comment '顺序',
    primary key (`id`)
)engine=innodb default charset=utf8mb4 comment = '分类';

INSERT INTO `wiki`.`category`(`id`, `parent`, `name`, `sort`) VALUES ('100', '000', '前端开发', 100);
INSERT INTO `wiki`.`category`(`id`, `parent`, `name`, `sort`) VALUES ('101', '100', 'Vue', 101);
INSERT INTO `wiki`.`category`(`id`, `parent`, `name`, `sort`) VALUES ('200', '000', 'java', 200);
INSERT INTO `wiki`.`category`(`id`, `parent`, `name`, `sort`) VALUES ('201', '200', '基础', 201);
INSERT INTO `wiki`.`category`(`id`, `parent`, `name`, `sort`) VALUES ('300', '000', 'Python', 300);

-- 文档表
create table `doc`
(
     `id` varchar(50) not null comment 'id',
     `ebook_id` varchar(50) not null default '0' comment '电子书ID',
     `parent` varchar(50) not null default '0' comment '父ID',
     `name` varchar(200) not null comment '名称',
     `sort` int comment '顺序',
     `view_count` int default 0 comment '阅读数',
      `vote_count` int default 0 comment '点击数',
      primary key (`id`)
)engine=innodb default charset=utf8mb4 comment = '文档';

INSERT INTO `wiki`.`doc`(`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`) VALUES ('1', '1', '0', '文档1', 1, 0, 0);
INSERT INTO `wiki`.`doc`(`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`) VALUES ('2', '1', '1', '文档1.1', 1, 0, 0);
INSERT INTO `wiki`.`doc`(`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`) VALUES ('3', '1', '0', '文档2', 2, 0, 0);
INSERT INTO `wiki`.`doc`(`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`) VALUES ('4', '1', '3', '文档2.1', 1, 0, 0);
INSERT INTO `wiki`.`doc`(`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`) VALUES ('5', '1', '3', '文档2.2', 2, 0, 0);
INSERT INTO `wiki`.`doc`(`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`) VALUES ('6', '1', '5', '文档2.2.1', 1, 0, 0);

-- 文档内容，文档内容很多所以需要分出来表，否则加入到一张表时候，分页查询会影响性能 这个叫做 纵向分表
create table `content`(
    `id` varchar(200) not null comment 'id',
    `content` mediumtext not null comment '内容',
    primary key (`id`)
)engine=innodb default charset=utf8mb4 comment '文档内容';

DROP TABLE IF EXISTS `content`;
CREATE TABLE `content`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;







