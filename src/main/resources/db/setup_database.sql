drop database hpg;
create database hpg;

drop owned by hpg;
drop user hpg;
create user hpg with password 'hpg';

grant all privileges on database hpg to hpg;

grant all on schema public to hpg;

grant usage, create on schema public to hpg;

alter database hpg owner to hpg;