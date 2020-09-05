DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS showCast;
DROP TABLE IF EXISTS episodes;

create table schedule
(
   id int not null,
   name varchar(255) not null,
   image varchar(255) not null,
   firstUnwatchedEpisodeId varchar(255) not null,
   primary key(id)
);

create table showCast
(
   id int not null,
   name varchar(255) not null,
   image varchar(255) not null,
   tvShowId int not null,
   primary key(id,tvShowId),
   foreign key (tvShowId) references schedule(id)
);

create table episodes
(
   id int not null,
   name varchar(255) not null,
   season varchar(255) not null,
   episodeNumber int not null,
   episodeAirDate date not null,
   unwatched number(1) not null,
   tvShowId int not null,
   primary key(id),
   foreign key (tvShowId) references schedule(id)
);


