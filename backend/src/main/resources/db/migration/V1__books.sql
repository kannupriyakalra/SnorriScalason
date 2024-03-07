create table if not exists books (
  id serial primary key,
  isbn character varying(13) unique not null,
  name text not null,
  author text not null,
  pages integer not null,
  published_year integer not null,
  created_at timestamp DEFAULT (now() at time zone 'utc') not null,
  updated_at timestamp DEFAULT (now() at time zone 'utc') not null
);
