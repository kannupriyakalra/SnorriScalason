package snorri

import cats.effect.IO
import io.circe.syntax.*
import io.circe.{Encoder, Json}
import krop.all.*
import krop.route.InvariantEntity
import org.http4s.circe.{CirceEntityDecoder, CirceEntityEncoder}
import snorri.models.Book
import snorri.repositories.BooksDb

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

  val getAllBooksRoute =
    Route(
      Request.get(Path.root / "books"),
      Response.ok(jsonEntity)
    ).handle(() => BooksDb.all.values.asJson)

  val getBookByIdRoute = {
    Route(
      Request.get(Path.root / "books" :? Query("id", Param.string)),
      Response.ok(jsonEntity).orNotFound
    ).handle(BooksDb.find(_).map(_.asJson))
  }
}
