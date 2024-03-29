/*
create user MazeUser@localhost identified by 'M@zeUs3r360';
*/
/* Initialize Database */
create database CAB302Maze;
use CAB302Maze;

grant select,update,insert,delete on CAB302Maze.* to MazeUser@localhost;
flush privileges;

/* ----------------------------------------------------------------- */
/*                              TABLES                               */
/* ----------------------------------------------------------------- */

/*                                                                   */
/* LOOKUP TABLES                                                     */
/*                                                                   */

create table WallType (
	Id int default 0 not null,
    Description varchar(40) default "" not null);
    
alter table WallType
	add constraint
    primary key (Id);

insert into WallType (Id, Description) values (1, "Empty");
insert into WallType (Id, Description) values (2, "Wall");

create table ImageType (
	Id int default 0 not null,
    Description varchar(40) default "" not null);
    
alter table ImageType
	add constraint
    primary key (Id);

insert into ImageType (Id, Description) values (1, "LogoEmpty");
insert into ImageType (Id, Description) values (2, "Logo");
insert into ImageType (Id, Description) values (3, "EntryImageEmpty");
insert into ImageType (Id, Description) values (4, "EntryImage");
insert into ImageType (Id, Description) values (5, "ExitImageEmpty");
insert into ImageType (Id, Description) values (6, "ExitImage");

/*                                                                   */
/* STATIC TABLES                                                     */
/*                                                                   */

/*                                                                   */
/* DYNAMIC TABLES                                                    */
/*                                                                   */

/* Mazes */
create table Maze(
  Id int default 0 not null,
  Deleted bool default false not null,
  Description varchar(40) default "" not null,
  Author varchar(40) default "" not null,
  CreationDate DateTime default current_timestamp not null,
  LastEdited DateTime default current_timestamp not null,
  XDimension int not null,
  YDimension int not null
);

alter table Maze
  add constraint
  primary key (Id);

create sequence MazeId
  start with 1
  increment by 1;

create table Cell (
  Id int default 0 not null,
  MazeId int not null,
  ArrayListPos int not null,
  XPosistion int not null,
  YPosistion int not null,
  NorthWallTypeId int not null,
  EastWallTypeId int not null,
  SouthWallTypeId int not null,
  WestWallTypeId int not null);

alter table Cell
  add constraint
  primary key (Id);

create sequence CellId
  start with 1
  increment by 1;
  
alter table Cell
  add constraint FK_MazeId_C
  foreign key (MazeId)
  references Maze(Id);

alter table Cell
  add constraint FK_NorthWallTypeId_C
  foreign key (NorthWallTypeId)
  references WallType(Id);

alter table Cell
  add constraint FK_EastWallTypeId_C
  foreign key (EastWallTypeId)
  references WallType(Id);

alter table Cell
  add constraint FK_SouthWallTypeId_C
  foreign key (SouthWallTypeId)
  references WallType(Id);

alter table Cell
  add constraint FK_WestWallTypeId_C
  foreign key (WestWallTypeId)
  references WallType(Id);


CREATE TABLE MazeImageResource (
    Id INT DEFAULT 0 NOT NULL,
    MazeId INT NOT NULL,
    ImageTypeId INT NOT NULL,
    Image MEDIUMBLOB,
    PositionX INT,
    PositionY INT
);

alter table MazeImageResource
add constraint
primary key(Id);

create sequence MazeImageResourceId
	start with 1
    increment by 1;

alter table MazeImageResource
  add constraint FK_MazeId_MIR
  foreign key (MazeId)
  references Maze(Id);

alter table MazeImageResource
  add constraint FK_ImageTypeId_MIR
  foreign key (ImageTypeId)
  references ImageType(Id);

/* ----------------------------------------------------------------- */
/*                            TRIGGERS                               */
/* ----------------------------------------------------------------- */

delimiter ~

create trigger BI_Id_M before insert on Maze for each row
begin

    if ((new.Id is null) or (new.Id = 0)) then
	  set new.Id = nextval(MazeId);
	end if;

end~

~

delimiter ;

delimiter ~

create trigger BI_Id_C before insert on Cell for each row
begin

    if ((new.Id is null) or (new.Id = 0)) then
	  set new.Id = nextval(CellId);
	end if;

end~

~

delimiter ;

delimiter ~

create trigger BI_Id_MIR before insert on MazeImageResource for each row
begin

    if ((new.Id is null) or (new.Id = 0)) then
	  set new.Id = nextval(MazeImageResourceId);
	end if;

end~

~

delimiter ;


commit work;
