use `like`;

create table dislike_action
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime null,
    update_time datetime null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);


create table dislike_target_count
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime null,
    update_time datetime null,
    vsn         int         not null,
    count       bigint      not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    constraint unq_target
        unique (target_id, target_type)
);


create table like_action
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime null,
    update_time datetime null,
    vsn         int         not null,
    status      char(16)    not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    user_id     bigint      not null,
    constraint unq_user_target
        unique (user_id, target_type, target_id)
);


create table like_target_count
(
    id          bigint auto_increment primary key,
    create_time datetime    not null,
    delete_time datetime null,
    update_time datetime null,
    vsn         int         not null,
    count       bigint      not null,
    target_id   bigint      not null,
    target_type varchar(16) not null,
    constraint unq_target
        unique (target_id, target_type)
);

