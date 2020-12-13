CREATE SCHEMA `coursework` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `user`
(
    `login`    VARCHAR(45) NOT NULL,
    `password` VARCHAR(25) NOT NULL,
    `isAdmin`  TINYINT     NOT NULL DEFAULT 0,
    `name`     VARCHAR(45) NOT NULL,
    PRIMARY KEY (`login`)
);

CREATE TABLE `teacher`
(
    `id_teacher` INT         NOT NULL auto_increment,
    `university` varchar(80) NOT NULL,
    `login`    VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id_teacher`),
    FOREIGN KEY (`login`) REFERENCES `user` (`login`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `course`
(
    `id_course` INT         NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(45) NOT NULL,
    `level`     VARCHAR(2)  NOT NULL,
    `cost`      DOUBLE      NOT NULL,
    PRIMARY KEY (`id_course`)
);

CREATE TABLE `class`
(
    `id_class`   INT        NOT NULL auto_increment,
    `id_course`  INT,
    `id_teacher` INT,
    `time`       time       not null,
    `days_code`  varchar(7) not null,
    PRIMARY KEY (`id_class`),
    FOREIGN KEY (`id_teacher`) REFERENCES `teacher` (`id_teacher`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`id_course`) REFERENCES `course` (`id_course`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `student`
(
    `id_student` INT         NOT NULL auto_increment,
    `id_class`   INT,
    `name`       varchar(45) NOT NULL,
    `surname`    varchar(45) NOT NULL,
    `email`      varchar(45) NOT NULL,
    `level`      varchar(2)  NOT NULL,
    `date_start` date        not null,
    `isPaid`     TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id_student`),
    FOREIGN KEY (`id_class`) REFERENCES `class` (`id_class`) ON DELETE CASCADE ON UPDATE CASCADE
);

create table `test`
(
    `id_test` INT     NOT NULL auto_increment,
    `id_student`  INT     NOT NULL,
    `result_test` INT,
    PRIMARY KEY (`id_test`),
    FOREIGN KEY (`id_student`) REFERENCES `student` (`id_student`) ON DELETE CASCADE ON UPDATE CASCADE
);

create table `attendance`
(
    `id_attendance` INT     NOT NULL auto_increment,
    `id_student`  INT     NOT NULL,
    `isPresent`   TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id_attendance`),
    FOREIGN KEY (`id_student`) REFERENCES `student` (`id_student`) ON DELETE CASCADE ON UPDATE CASCADE
);

