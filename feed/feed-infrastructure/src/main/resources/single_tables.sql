use feed;
create table feed
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


create table feed_box
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

