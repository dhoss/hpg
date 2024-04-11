create table feed_url_history(
  id integer not null generated always as identity primary key,
  url varchar(2000) not null unique,
  hash char(64) not null unique,
  created timestamptz not null default now(),
  updated timestamptz
);