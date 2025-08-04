create table test_message
(
    id           bigint auto_increment primary key,

    orderly      tinyint      not null comment '是否为顺序消息',

    topic        varchar(64)  not null comment 'MQ topic',
    sharding_key varchar(128) not null comment 'ShardingKey，用于选择不同的 partition',
    tag          varchar(128) not null comment 'Message Tag 信息',

    msg_id       varchar(64)  not null comment 'Msg ID 只有发送成功后才有数据',
    msg_key      varchar(64)  not null comment 'MSG Key，用于查询数据',
    msg          longtext     not null comment '要发送的消息',

    retry_time   tinyint      not null comment '重试次数',
    status       tinyint      not null comment '发送状态:0-初始化，1-发送成功，2-发送失败',

    create_time  datetime     not null,
    update_time  datetime     not null,

    index idx_update_time_status(update_time, status)
);
