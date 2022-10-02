create table feeds(
  id integer not null generated always as identity primary key,
  name varchar(200) not null unique,
  updated_on timestamptz default now(),
  created_on timestamptz default now()
);

create table feed_items(
  id integer not null generated always as identity primary key,
  title varchar(200) not null unique,
  link text,
  description text,
  published timestamptz,
  updated_on timestamptz default now(),
  created_on timestamptz default now()
);

