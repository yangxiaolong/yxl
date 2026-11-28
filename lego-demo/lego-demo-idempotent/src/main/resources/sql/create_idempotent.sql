CREATE TABLE `idempotent_execution_record`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `type`        int(11) NOT NULL,-- 业务分组，对应@Idempotent注解的 group
    `unique_key`  varchar(64) NOT NULL,-- 幂等键
    `status`      int(11) NOT NULL,-- 0 未执行 / 1 执行中 / 2 成功 / 3 失败
    `result`      varchar(1024) DEFAULT NULL,-- 返回值或异常类名
    `create_date` datetime      DEFAULT NULL,
    `update_date` datetime      DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unq_type_key` (`type`,`unique_key`)--唯一索引 `unq_type_key`，它是 DB 方案能挡住并发重复插入的关键
) ENGINE=InnoDB;