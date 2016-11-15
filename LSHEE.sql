create database if not exists lshee1;
use lshee1;
create table if not exists user(
	id int AUTO_INCREMENT primary key,
    username varchar(30) not null,
    pass_word varchar(20) not null,
    phone_number varchar(50) not null,
    email varchar(50) not null
)ENGINE = INNODB;

create table if not exists friend(
	id int AUTO_INCREMENT primary key,
    sender int not null,
    receiver int not null,
    datesend varchar(50) not null,
    isfriend bool not null
)ENGINE = INNODB;
create table if not exists message(
	id int AUTO_INCREMENT primary key,
    room varchar(50) not null,
    sender int not null,
    receiver int not null,
    message text not null,
    datesend varchar(50) not null 
)ENGINE = INNODB;
use lshee1;
insert into user values (null, 'hangphp', '12345678', '01898396323','thuhag@g.com');
insert into user values (null, 'tn', '12345678', '01898396323','thuhag@g.com');
insert into user values (null, 'hang', '12345678', '01898396323','thuhag@g.com');

insert into friend values (null, 1, 2, '1/1/2016', true);
insert into friend values (null, 2, 3, '1/1/2016', true);
insert into friend values (null, 1, 2, '1/1/2016', true);

insert into message values (null,'room1', 3, 2,'hi', '1/1/2016');