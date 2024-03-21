create table if not exists books (
  id serial primary key,
  isbn character varying(16) unique not null,
  name text not null,
  author text not null,
  pages integer not null,
  published_year integer not null,
  created_at timestamp DEFAULT (now() at time zone 'utc') not null,
  updated_at timestamp DEFAULT (now() at time zone 'utc') not null
);

INSERT INTO books(isbn, name, author, pages, published_year) VALUES
  (
    '978-0641723445',
    'The Lightning Thief',
    'Rick Riordan',
    384,
    2002
  ),
  (
    '978-1423103349',
    'The Sea of Monsters',
    'Rick Riordan',
    304,
    1978
  ),
  (
    '978-1857995879',
    'Sophie''s World : The Greek Philosophers',
    'Jostein Gaarder',
      64,
      2012
),
(
  '978-1933988177',
  'Lucene in Action, Second Edition',
  'Michael McCandless',
  475,
  1564
);
