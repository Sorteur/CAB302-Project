/*
create user MazeUser@localhost identified by 'M@zeUs3r360';
create user MazeAdmin@localhost identified by 'M@ze@dm1n720';
*/
/* Initialize Database */
create database CAB302Maze;
use CAB302Maze;

grant select,update,insert,delete on CAB302Maze.* to MazeUser@localhost;
grant all privileges on CAB302Maze to MazeAdmin@localhost;
flush privileges;

/* ----------------------------------------------------------------- */
/*                              TABLES                               */
/* ----------------------------------------------------------------- */



/*                                                                   */
/* LOOKUP TABLES                                                  */
/*                                                                   */

create table WallType (
	Id int default 0 not null,
    Description varchar(40) default "" not null);
    
alter table WallType
	add constraint
    primary key (Id);

insert into WallType (Id, Description) values (1, "Wall");
insert into WallType (Id, Description) values (2, "Empty");
insert into WallType (Id, Description) values (3, "ImageWall");

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
/* TODO WallTypeId FK
alter table Cell
  add constraint FK_MazeId_C
  foreign key (MazeId)
  references Maze(Id);
*/

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

commit work;
