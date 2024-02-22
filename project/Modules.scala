import sbt.*

object Modules {
  val CirceVersion = "0.14.6"
  val FlywayVersion = "9.15.1"

  lazy val circe: List[ModuleID] =
    "io.circe"   %% "circe-parser"  % CirceVersion ::
      "io.circe" %% "circe-generic" % CirceVersion ::
      Nil


  lazy val flyway: List[ModuleID] = 
    "org.flywaydb" % "flyway-core" % FlywayVersion :: Nil

}
