# SnorriScalason

ScalaBridge Project - mentored by Zainab Ali & Noel Welsh

## Frontend

### Running

To run the frontend you need to compile the code in sbt and then do some Javascript setup (which requires you have `npm` installed on your machine.)

First, the Scala side of things. From within `sbt` run

```scala
frontend / fastOptJS
```

Now the Javascript side. From a terminal, change into the `frontend` directory

```sh
cd frontend
```

Install Javascript dependencies

```sh
npm install
```

Start the web server running the frontend

```sh
npm start
```

Visit [localhost:1234](http://localhost:1234/) and you should see the frontend.


### Developing

Code is in `src/main/{css,html,scala}`

### Mar 7, 2024



### Feb 22, 2024

- We attempted to setup a database with a table to store book. We decided to run this all as tasks from sbt using
  - Docker to create the database
  - Flyway to populate the database

- Errors to resolve:
  - snorri": -c: line 1: unexpected EOF while looking for matching `"'
  - how do we specify flyway files location to Flyway.configure

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
