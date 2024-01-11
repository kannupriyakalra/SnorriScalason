package snorri

import krop.all.*
import krop.route.InvariantEntity
import org.http4s.headers.`Content-Type`
import org.http4s.syntax.all.mediaType
import org.http4s.circe._
import cats.effect.IO
import io.circe.Json
import io.circe.parser._

object Snorri {
  // Read some json from a file
  // - adding circe as a dependency
 val testJson = """[{
      "id" : "978-0641723445",
      "cat" : ["book","hardcover"],
      "name" : "The Lightning Thief",
      "author" : "Rick Riordan",
      "series_t" : "Percy Jackson and the Olympians",
      "sequence_i" : 1,
      "genre_s" : "fantasy",
      "inStock" : true,
      "price" : 12.50,
      "pages_i" : 384
    }]"""

   // Read the books.json file as a string
   def readBooks: String = testJson

    def decode(jsonString: String): List[Book] = parce(jsonString)

    final case class Book(id: String)

    def encodeAsJson(book: List[Book]): Json = ???

  val echoRoute =
    Route(
      Request.get(Path.root / "echo" / Param.string),
      Response.ok(Entity.text)
    ).handle(param => s"Your message was ${param}")

// Write a route that serves some json
// entity encoding with krop
//
 val json: InvariantEntity[Json] =
    Entity(
      CirceEntityDecoder.circeEntityDecoder,
      CirceEntityEncoder.circeEntityEncoder
    )
  val getBookRoute = {
   Route(
      Request.get(Path.root / "books"),
      Response.ok(json/*.withContentType(`Content-Type`(mediaType"application/json"))*/)
    ).handle(_ => encodeAsJson(decode(readBooks)))
  }
}
