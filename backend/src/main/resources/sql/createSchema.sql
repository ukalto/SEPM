DROP TABLE IF EXISTS owner,horse,horse_has_children;
CREATE TABLE IF NOT EXISTS owner(
    ownerID            BIGINT AUTO_INCREMENT PRIMARY KEY,
    prename VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS horse
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    description          VARCHAR(255),
    birthdate          DATE NOT NULL,
    sex          VARCHAR(255) NOT NULL,
    ownerID          BIGINT,
    horseFatherID          BIGINT,
    horseMotherID          BIGINT
);

--- Start owner inserts
insert into owner (prename, surname, email) values ('Carlynn', 'Quoit', 'cquoit0@123-reg.co.uk');
insert into owner (prename, surname, email) values ('Ripley', 'Jantot', 'rjantot1@360.cn');
insert into owner (prename, surname, email) values ('Levin', 'Cunah', 'lcunah2@dailymotion.com');
insert into owner (prename, surname, email) values ('Demetria', 'Kerrich', 'dkerrich3@shareasale.com');
insert into owner (prename, surname, email) values ('Ceciley', 'Georgi', 'cgeorgi4@desdev.cn');
insert into owner (prename, surname, email) values ('Sharla', 'Biaggioli', 'sbiaggioli5@photobucket.com');
insert into owner (prename, surname, email) values ('Cosette', 'Skarin', 'cskarin6@abc.net.au');
insert into owner (prename, surname, email) values ('Flossie', 'Tebbe', 'ftebbe7@census.gov');
insert into owner (prename, surname, email) values ('Kasey', 'Seak', 'kseak8@chronoengine.com');
insert into owner (prename, surname, email) values ('Kim', 'Decort', 'kdecort9@usnews.com');
insert into owner (prename, surname, email) values ('Sherm', 'Chalk', 'schalka@slideshare.net');
insert into owner (prename, surname, email) values ('Ashley', 'Livsey', 'alivseyb@dropbox.com');
insert into owner (prename, surname, email) values ('Annaliese', 'Lehmann', 'alehmannc@hexun.com');
insert into owner (prename, surname, email) values ('Chev', 'Bohden', 'cbohdend@free.fr');
insert into owner (prename, surname, email) values ('Marlie', 'Skepper', 'mskeppere@symantec.com');
insert into owner (prename, surname, email) values ('Ettore', 'Samwayes', 'esamwayesf@java.com');
insert into owner (prename, surname, email) values ('Nari', 'Metschke', 'nmetschkeg@reverbnation.com');
insert into owner (prename, surname, email) values ('Fanya', 'Librey', 'flibreyh@oracle.com');
insert into owner (prename, surname, email) values ('Dane', 'Jefferd', 'djefferdi@archive.org');
insert into owner (prename, surname, email) values ('Adena', 'Crighten', 'acrightenj@wsj.com');
--- End owner inserts

INSERT INTO horse (id,name,description,birthdate,sex,ownerID,horseFatherID,horseMotherID)
VALUES (-1,'Wendy','hh','2000-12-05','MALE',6,NULL,NULL)
;
INSERT INTO horse (name,description,birthdate,sex,ownerID,horseFatherID,horseMotherID)
VALUES ('peter','hh','2001-12-05','MALE',2,NULL,NULL)
;
INSERT INTO horse (name,description,birthdate,sex,ownerID,horseFatherID,horseMotherID)
VALUES ('marie','hh','2002-12-05','FEMALE',3,-1,NULL)
;
INSERT INTO horse (name,description,birthdate,sex,ownerID,horseFatherID,horseMotherID)
VALUES ('melvin','hh','2000-11-05','MALE',4,NULL,NULL)
;
INSERT INTO horse (name,description,birthdate,sex,ownerID,horseFatherID,horseMotherID)
VALUES ('max','sssss','2004-12-05','MALE',1,-1,2)
;
