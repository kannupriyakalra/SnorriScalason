import sbt.*

object Modules {
  val CirceVersion = "0.14.6"

  lazy val circe: List[ModuleID] =
    "io.circe"   %% "circe-parser"  % CirceVersion ::
      "io.circe" %% "circe-generic" % CirceVersion ::
      Nil
}
