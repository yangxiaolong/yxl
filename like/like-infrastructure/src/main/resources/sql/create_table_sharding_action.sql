use like_action_0;

create table dislike_action_0
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_1
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_2
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_3
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_4
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_5
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_6
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_7
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_0
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_1
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_2
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_3
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_4
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_5
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);


create table like_action_6
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);


create table like_action_7
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);


use like_action_1;

create table dislike_action_8
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_9
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_10
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_11
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_12
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_13
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_14
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table dislike_action_15
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_8
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_9
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_10
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_11
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_12
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

create table like_action_13
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);


create table like_action_14
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);


create table like_action_15
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime    null,
    update_time datetime    null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);

