package snorri

import cats.effect.IO
import io.circe.parser.decode
import io.circe.syntax.*
import io.circe.{Encoder, Json}
import krop.all.*
import krop.route.InvariantEntity
import org.http4s.circe.{CirceEntityDecoder, CirceEntityEncoder}

import scala.io.{BufferedSource, Source}

object Snorri {
  private val jsonEntity: InvariantEntity[Json] =
    Entity(
      CirceEntityDecoder.circeEntityDecoder,
      CirceEntityEncoder.circeEntityEncoder
    )

  val echoRoute =
    Route(
      Request.get(Path.root / "echo" / Param.string),
      Response.ok(Entity.text)
    ).handle(param => s"Your message was $param")

  val getBooksRoute =
    Route(
      Request.get(Path.root / "books"),
      Response.ok(jsonEntity)
    ).handle(() => loadBooks().asJson)

  private def loadBooks(): List[Book] =
    decode[List[Book]](retrieveBooks()) match {
      case Right(books) => books
      case Left(err) =>
        err.fillInStackTrace().printStackTrace()
        throw err.getCause
    }

  private def retrieveBooks(): String =
    use(booksResource())(_.mkString)

  private def booksResource(): BufferedSource = {
    val stream = getClass.getResourceAsStream("/book.json")
    Source.fromInputStream(stream)
  }
}
