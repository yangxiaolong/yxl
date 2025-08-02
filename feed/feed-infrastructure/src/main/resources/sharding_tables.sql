use feed_data_0;
create table feed_0
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_1
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_2
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_3
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_4
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_5
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_6
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_7
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

use feed_data_1;
create table feed_8
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_9
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_10
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_11
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_12
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_13
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_14
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);

create table feed_15
(
    id          bigint auto_increment        primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    content     text null,
    type        varchar(64) null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    status      varchar(64) null
);


use feed_box_0;

create table feed_box_0
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_1
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_2
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_3
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_4
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_5
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_6
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_7
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);


use feed_box_1;

create table feed_box_8
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_9
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_10
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_11
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_12
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_13
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_14
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);

create table feed_box_15
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    data        longtext     null,
    owner_id    bigint       null,
    owner_type  varchar(64) null,
    item_count  int          null,
    max_score   bigint       null,
    min_score   bigint       null,
    type        varchar(64) null
);