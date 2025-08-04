CREATE TABLE `idempotent_execution_record`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `type`        int(11) NOT NULL,
    `unique_key`  varchar(64) NOT NULL,
    `status`      int(11) NOT NULL,
    `result`      varchar(1024) DEFAULT NULL,
    `create_date` datetime      DEFAULT NULL,
    `update_date` datetime      DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unq_type_key` (`type`,`unique_key`)
) ENGINE=InnoDB;