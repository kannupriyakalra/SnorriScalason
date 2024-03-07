import scala.sys.process.*

object DbTasks {
  def createDb(dbName: String, containerName: String): Unit = {
    val dockerCmd = List(
      "docker",
      "exec",
      containerName,
      "su",
      "-",
      "postgres",
      "-c",
      s"createdb $dbName"
    )

    println(dockerCmd.mkString(" "))

    (dockerCmd #|| List("echo", "Ignoring error and continuing")).!
  }

  def createTrgm(dbName: String, containerName: String): Unit = {
    val cmd = List(
      "docker",
      "exec",
      containerName,
      "su",
      "-",
      "postgres",
      "-c",
      s"psql $dbName -c 'CREATE EXTENSION IF NOT EXISTS pg_trgm;'"
    )

    println(cmd.mkString(" "))

    (cmd #|| List("echo", "Ignoring error and continuing")).!
  }
}
