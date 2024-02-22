package snorri.models

import io.circe.generic.semiauto.*
import io.circe.{Decoder, Encoder}

// NOTE: Not yet reading series_t,sequence_i and cat
final case class Book(
  id:      String, // TBD: id is ISBN but should id here be different from ISBN?
  name:    String,
  author:  String,
  genres:  String,
  inStock: Boolean,
  price:   Double,
  pages:   Int
)

object Book:
  implicit val bookEncoder: Encoder[Book] = deriveEncoder[Book]
  implicit val bookDecoder: Decoder[Book] = deriveDecoder[Book]
