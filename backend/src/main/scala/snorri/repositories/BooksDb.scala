package snorri.repositories

import io.circe.parser.decode
import krop.all.*
import snorri.models.Book
import snorri.utils.use

import scala.io.{BufferedSource, Source}

object BooksDb:
  val all: Map[String, Book] = loadBooks()

  def find(id: String): Option[Book] =
    all.get(id)

  private def loadBooks(): Map[String, Book] =
    decode[List[Book]](retrieveBooks()) match {
      case Right(books) => books.map(b => b.id -> b).toMap
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
