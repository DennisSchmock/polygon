

drop table if exists building;
ALTER SCHEMA `Polygon`  DEFAULT COLLATE utf8 ;

CREATE table building(
idbuilding int(10) PRIMARY KEY auto_increment,
building_name varchar(40),
building_m2 double,
building_adress varchar(100),
building_housenumber int(4),
building_buildyear int(4),
building_zip int(4),
building_use varchar(100)
);

CREATE table building_pic(
building_pic_id int (10) PRIMARY KEY auto_increment,
building_pic_extension varchar(4),
building_id int (10),
foreign key (building_id) references building (idbuilding)
);
select * from building;
select * from building_pic;

CREATE table building_room(
room_id int(10) PRIMARY KEY auto_increment,
room_name varchar(40),
building_id int (10),
foreign key (building_id) REFERENCES building (idbuilding)

);

delete from building_room where room_id>0;

insert into Building (building_name, building_m2,building_adress,building_buildyear,building_housenumber,building_pic,building_use,building_zip)
values ('Vaskeriet',146,'Nørregaardsvej',1978,28,1724,'Student wokrspace',2800);

select * from Building;


