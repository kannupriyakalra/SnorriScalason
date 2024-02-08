package snorri.models

import io.circe.generic.semiauto.*
import io.circe.{Decoder, Encoder}

// NOTE: Not yet reading series_t,sequence_i and cat
case class Book(
  id:      String,
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
