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

