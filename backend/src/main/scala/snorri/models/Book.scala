package snorri.models

import io.circe.generic.semiauto.*
import io.circe.{Decoder, Encoder}
import skunk.codec.all.*
import skunk.implicits.*
import skunk.*

final case class Book(
  id:            Int,
  isbn:          String,
  name:          String,
  author:        String,
  pages:         Int,
  publishedYear: Int
)

object Book:
  implicit val bookEncoder: Encoder[Book] = deriveEncoder[Book]
  implicit val bookDecoder: Decoder[Book] = deriveDecoder[Book]
