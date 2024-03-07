import scala.sys.process.*
import org.flywaydb.core.Flyway
import DbTasks.*

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion    := "3.3.1"
ThisBuild / tlBaseVersion   := "0.1"
ThisBuild / tlCiHeaderCheck := false

// Remove dependency submission, which is failing and is not worth setting up for this project
ThisBuild / githubWorkflowAddedJobs ~= (jobs => jobs.filter(job => job.id != "dependency-submission"))

val startDb = taskKey[Unit]("Start the Postgres Docker container")
val initDb  = taskKey[Unit]("Create database tables and add initial data")

val databaseDirectory     = settingKey[File]("The directory on the local file system where the database is stored.")
val databaseContainerName = settingKey[String]("The name of the Docker container running the database.")
val databaseName          = settingKey[String]("The name of the Snorri database.")

val commonSettings = Seq(
  libraryDependencies ++=
    "org.creativescala" %% "krop-core" % "0.7.0" ::
      Modules.circe
)

lazy val snorriRoot =
  project
    .in(file("."))
    .aggregate(backend, frontend, integration)

lazy val backend: Project =
  project
    .in(file("backend"))
    .settings(
      commonSettings,
      // This sets Krop into development mode, which gives useful tools for
      // developers. If you don't set this, Krop runs in production mode.
      run / javaOptions += "-Dkrop.mode=development",
      run / fork            := true,
      databaseDirectory     := baseDirectory.value / "data",
      databaseContainerName := "snorri-db",
      databaseName          := "snorri",
      startDb := {
        s"mkdir ${databaseDirectory.value}".!
        s"docker run --name ${databaseContainerName.value} -p 5432:5432 -v ${databaseDirectory.value}/snorri-db -e POSTGRES_PASSWORD=password -d postgres:latest".!
      },
      initDb := {
        createDb(databaseName.value, databaseContainerName.value)
        createTrgm(databaseName.value, databaseContainerName.value)

        Flyway
          .configure(getClass.getClassLoader)
          .dataSource(
            s"jdbc:postgresql://localhost:5432/${databaseName.value}",
            "postgres",
            "password"
          )
          .schemas(databaseName.value)
          .driver("org.postgresql.Driver")
          .locations(s"filesystem:${(Compile / resourceDirectory).value / "db" / "migration"}")
          .validateMigrationNaming(true)
          .failOnMissingLocations(true)
          .outOfOrder(true)
          .load
          .migrate
      }
    )

lazy val frontend =
  project
    .in(file("frontend"))
    .settings(commonSettings)
    .settings(
      libraryDependencies += "io.indigoengine" %%% "tyrian-io" % "0.10.0",
      scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }
    )
    .enablePlugins(ScalaJSPlugin)

lazy val integration = (project in file("integration"))
  .dependsOn(backend)
  .settings(
    publish / skip := true,
    libraryDependencies ++=
      "com.dimafeng"    %% "testcontainers-scala-scalatest"  % "0.41.2" % Test ::
        "com.dimafeng"  %% "testcontainers-scala-postgresql" % "0.41.2" % Test ::
        "org.postgresql" % "postgresql"                      % "42.7.1" % Test ::
        "org.scalatest" %% "scalatest"                       % "3.2.18" % Test ::
        Nil
  )
