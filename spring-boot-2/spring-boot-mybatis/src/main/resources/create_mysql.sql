CREATE SEQUENCE seq_employee START WITH 1 INCREMENT BY 1 CACHE = 20;

CREATE SEQUENCE seq_address START WITH 1 INCREMENT BY 1  CACHE = 20;

CREATE TABLE IF NOT EXISTS `employee`
(
    `employee_id` int(11)     NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) NOT NULL,
    `phone`       varchar(50) NOT NULL,
    `email`       varchar(50) NOT NULL,
    `gender`      varchar(50) NOT NULL,
    `status`      varchar(50) NOT NULL,
    `create_date` datetime    NOT NULL,
    PRIMARY KEY (`employee_id`),
    UNIQUE KEY `phone` (`phone`),
    UNIQUE KEY `email` (`email`),
    FULLTEXT KEY `name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



CREATE TABLE `address`
(
    `address_id`     int(11)      AUTO_INCREMENT,
    `employee_id`    int(11)      NOT NULL,
    `street_address` varchar(500) NOT NULL,
    `postal_code`    varchar(50)  NOT NULL,
    `create_date`    datetime         NOT NULL,
    PRIMARY KEY (`address_id`)
) ENGINE = InnoDB;
