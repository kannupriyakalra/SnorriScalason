Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion    := "3.3.1"
ThisBuild / tlBaseVersion   := "0.1"
ThisBuild / tlCiHeaderCheck := false

val commonSettings = Seq(
  libraryDependencies ++=
    "org.creativescala" %% "krop-core" % "0.7.0" ::
      Modules.circe
)

lazy val snorriRoot =
  project
    .in(file("."))
    .aggregate(backend, frontend, integration)

lazy val backend =
  project
    .in(file("backend"))
    .settings(
      commonSettings,
      // This sets Krop into development mode, which gives useful tools for
      // developers. If you don't set this, Krop runs in production mode.
      run / javaOptions += "-Dkrop.mode=development",
      run / fork := true
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
        "org.postgresql" % "postgresql"                      % "42.5.1" % Test ::
        "org.scalatest" %% "scalatest"                       % "3.2.18" % Test ::
        Nil
  )
