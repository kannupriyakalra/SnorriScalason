# SnorriScalason

> ScalaBridge Project - mentored by Zainab Ali & Noel Welsh


### Mar 21, 2024

- API to add book
  - Added basic scaffold for add book implementation. But the request is being dropped with a 500 ISE. Most likely because the request body is not being seen/parsed by `Snorri.addBookRoute` definition.
  - TODOs
    - It's time to add a service layer where you can do business logic and validations
    - Add table that holds the price of the book, and other details
- API to rate a book (1/5 stars)
- Find a way to import csv/json file with books into the DB (seed data)
- FUTURE: Need help from Zainab using FS2 to stream books (for `getAllBooks`)

### Mar 7, 2024

- Got the sbt-DB tasks working
- Got the Flyway migration working
- Started working on using Skunk
- Implemented find book api using Skunk (naively)

### Feb 22, 2024

- We attempted to setup a database with a table to store book. We decided to run this all as tasks from sbt using
  - Docker to create the database
  - Flyway to populate the database

- [x] Errors to resolve:
  - [x] snorri": -c: line 1: unexpected EOF while looking for matching `"'
  - [x] how do we specify flyway files location to Flyway.configure

- We spent a lot of time doing devops style work. Not very much programming.

### Feb 8, 2024
  - Set a target: We're going all into web development! Plus DBs. 
  - Choose libs for DB: postgresql / skunk, UI: Tyrian
  - Set up testcontainers: add integration module, check if postgres image is up and running 
  - Wrote a test with ScalaTest.
  - The testcontainers docs are out of date.
  - Learned how to troubleshoot docker containers:
    - Looking at Docker desktop to see running containers.

### Jan 25, 2024

- Get a book with a given id
  - Created `getBookById` route
  - Currently blocked by a feature that is not available in krop today. But hopefully coming up soon. Noel demonstrated how krop would be updated to accomodate the missing feature.
-  Use the updated version of krop with updates Noel demonstrated
- Get a book with a given id

## Jan 11, 2024

- Went through PRs and merged them.
- Updated the krop version to ``0.6.0`.
- Run and call the `echo`.
- Created a skeleton for encoding and decoding JSON.
