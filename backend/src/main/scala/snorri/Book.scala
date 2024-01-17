package snorri

import io.circe.{Encoder, Decoder}
import io.circe.generic.semiauto.*

// NOTE: Not yet reading series_t,sequence_i and cat
case class Book(
  id: String,
  name: String,
  author: String,
  genres: String,
  inStock: Boolean,
  price: Double,
  pages: Int
)

object Book:
  implicit val bookEncoder: Encoder[Book] = deriveEncoder[Book]
  implicit val bookDecoder: Decoder[Book] = deriveDecoder[Book]
