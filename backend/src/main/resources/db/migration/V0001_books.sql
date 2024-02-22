create table if not exists books (
  id serial primary key,
  isbn character varying(13) unique non null,
  name text non null,
  author text non null,
  pages integer non null,
  published_year integer non null,
  created_at timestamp DEFAULT (now() at time zone 'utc') not null,
  updated_at timestamp DEFAULT (now() at time zone 'utc') not null
);
