create table number_generator
(
    id             bigint auto_increment primary key,
    current_number bigint      null,
    type           varchar(16) null,
    version        bigint      not null
);

