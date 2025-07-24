create database chat_studio;

use chat_studio;

create table kb
(
    id           bigint primary key key auto_increment,
    name         varchar(64) not null comment '知识库名',
    user_id      bigint      not null comment '所属用户',
    created_time datetime    not null                             default current_timestamp,
    updated_time datetime    not null on update current_timestamp default current_timestamp
) comment '知识库';

create table kb_store
(
    id            bigint primary key auto_increment,
    file_name     varchar(64)  not null comment '文件名',
    ext_name      varchar(16)  not null comment '文件扩展名',
    upload_path   varchar(256) not null comment '上传路径',
    upload_date   datetime     not null default current_timestamp comment '上传时间',
    upload_status tinyint      not null comment '上传状态 0-上传中 1-解析中 2-解析完成 -1-上传失败',
    kb_id         bigint       not null comment '知识库 id'
) comment '知识库存储';

create table user
(
    user_id      varchar(64) primary key,
    username     varchar(24)  not null comment '用户名',
    nick_name    varchar(24)  not null comment '昵称',
    password     varchar(128) not null comment '密码',
    capacity     int          not null default -1 comment '容量 -1无限制',
    created_time datetime     not null default current_timestamp,
    updated_time datetime     not null default current_timestamp on update current_timestamp
) comment '用户信息';