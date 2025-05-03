create table tb_order
(
    id              bigint primary key auto_increment,
    user_address_id bigint        null,
    user_id         varchar(32) null,
    status          varchar(32) null,
    price           int         null,
    vsn             int         null
);

create table tb_order_address
(
    id     bigint      primary key auto_increment,
    detail varchar(32) null
);

create table tb_order_item
(
    id           bigint      primary key auto_increment,
    order_id     int         null,
    amount       int         null,
    price        int         null,
    product_id   int         null,
    product_name varchar(32) null,
    status       varchar(32) null
);

select * from tb_order;
select * from tb_order_item;
select * from tb_order_address;

delete from tb_order;
delete from tb_order_item;
delete from tb_order_address;
