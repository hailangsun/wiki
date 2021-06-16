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












