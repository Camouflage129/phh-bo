CREATE DATABASE camouflage CHARACTER SET utf8 COLLATE utf8_bin;

select * from mysql.user where user='phh';
select * from mysql.db where user='phh';
flush privileges;

CREATE USER 'phh'@'localhost' IDENTIFIED BY 'phh!@#' PASSWORD EXPIRE NEVER;
grant all privileges on camouflage.* to 'phh'@'localhost';
CREATE USER 'phh'@'%' IDENTIFIED BY 'phh!@#' PASSWORD EXPIRE NEVER;
grant all privileges on camouflage.* to 'phh'@'%';
flush privileges;

use camouflage;

CREATE TABLE `TODO` (
  `ID` int(11) NOT NULL,
  `MEMO` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `DUE_DATE` date DEFAULT NULL,
  `MODIFIED_DATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
