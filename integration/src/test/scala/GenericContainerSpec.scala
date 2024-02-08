import com.dimafeng.testcontainers.PostgreSQLContainer
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import org.scalatest.flatspec.AnyFlatSpec
import org.testcontainers.utility.DockerImageName
import java.sql.DriverManager
import org.testcontainers.containers.Container

class PgSpec extends AnyFlatSpec with TestContainerForAll {

  override val containerDef = PostgreSQLContainer.Def(
    dockerImageName = DockerImageName.parse("postgres:15.1"),
    databaseName = "testcontainer-scala",
    username = "scala",
    password = "scala"
  )

  "PostgreSQL container" should "be started" in {
    withContainers { pgContainer =>
      Thread.sleep(5000)

      // pgContainer.stop()
      //  Class.forName(pgContainer.driverClassName)
      // val connection = DriverManager.getConnection(pgContainer.jdbcUrl, pgContainer.username, pgContainer.password)
      // assert(!connection.isClosed())
    }
  }
}
