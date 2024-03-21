package snorri.models

import io.circe.generic.semiauto.*
import io.circe.{Decoder as CirceDecoder, Encoder as CirceEncoder}
import skunk.*
import skunk.codec.all.*
import skunk.Decoder

case class AddBookInput(
  isbn:          String,
  name:          String,
  author:        String,
  pages:         Int,
  publishedYear: Int
)

object AddBookInput {
  implicit val circeEncoder: CirceEncoder[AddBookInput] = deriveEncoder[AddBookInput]
  implicit val circeDecoder: CirceDecoder[AddBookInput] = deriveDecoder[AddBookInput]
}

case class Book(
  id:            Int,
  isbn:          String,
  name:          String,
  author:        String,
  pages:         Int,
  publishedYear: Int
)

object Book:
  implicit val circeEncoder: CirceEncoder[Book] = deriveEncoder[Book]
  implicit val circeDecoder: CirceDecoder[Book] = deriveDecoder[Book]

  val skunkDecoder: Decoder[Book] =
    (int4 ~ varchar(16) ~ text ~ text ~ int4 ~ int4).map { case id ~ isbn ~ name ~ author ~ pages ~ publishedYear =>
      Book(id, isbn, name, author, pages, publishedYear)
    }
