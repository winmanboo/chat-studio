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
    user_id            varchar(64) primary key,
    email              varchar(36)  not null comment '邮箱',
    nick_name          varchar(24)  not null comment '昵称',
    pwd_hash           varchar(255) not null comment '密码',
    state              varchar(64)  not null comment '用户状态（INIT、ACTIVE、FROZEN）',
    invite_code        varchar(255)          default null comment '邀请码',
    inviter_id         varchar(255)          default null comment '邀请人id',
    capacity           int          not null default -1 comment '容量 -1无限制',
    profile_avatar_url varchar(255)          default null comment '用户头像地址',
    deleted            int                   default 0 comment '逻辑删除字段 0-未删除 1-已删除',
    created_time       datetime     not null default current_timestamp,
    updated_time       datetime     not null default current_timestamp on update current_timestamp
) comment '用户信息';

create table notice
(
    id              bigint unsigned not null primary key auto_increment comment 'id',
    created_time    datetime        not null default current_timestamp,
    updated_time    datetime        not null default current_timestamp on update current_timestamp,
    notice_title    varchar(512)    not null comment '通知标题',
    notice_content  text                     default null comment '通知内容',
    notice_type     varchar(128)    not null comment '通知类型',
    send_time       datetime        not null default current_timestamp comment '发送时间',
    receive_address varchar(256)             default null comment '接收地址',
    state           varchar(128)    not null comment '状态（INIT、SUCCESS、FAILED、SUSPEND）',
    deleted         int                      default 0 comment '逻辑删除字段 0-未删除 1-已删除',
    retry_times     int                      default 0 comment '重试次数',
    extend_info     varchar(1024)            default null comment '扩展信息'
)