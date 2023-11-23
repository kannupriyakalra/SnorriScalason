ThisBuild / scalaVersion := "3.3.1"

ThisBuild / tlBaseVersion := "0.1"

Global / onChangedBuildSource := ReloadOnSourceChanges

val commonSettings = Seq(
    libraryDependencies += "org.creativescala" %% "krop-core" % "0.6.0"
)

lazy val root = project.in(file(".")).aggregate(backend, frontend)

lazy val backend = project.in(file("backend")).settings(
    commonSettings, 
     // This sets Krop into development mode, which gives useful tools for
    // developers. If you don't set this, Krop runs in production mode.
    run / javaOptions += "-Dkrop.mode=development",
    run / fork := true,
    )

lazy val frontend = project.in(file("frontend")).settings(commonSettings)
