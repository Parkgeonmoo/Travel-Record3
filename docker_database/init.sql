ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
flush privileges;

create database if not exists trip_record;

use trip_record;