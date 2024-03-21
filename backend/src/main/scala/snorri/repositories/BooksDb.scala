package snorri.repositories

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import io.circe.parser.decode
import natchez.Trace.Implicits.noop
import skunk.codec.all.varchar
import skunk.implicits.sql
import skunk.{Query, Session}
import snorri.models.Book
import snorri.utils.use

import scala.io.{BufferedSource, Source}

object BooksDb:
  val all: Map[String, Book] = loadBooks()

  def find(isbn: String): Option[Book] =
    val query: Query[String, Book] =
      sql"""SELECT id, isbn, name, author, pages, published_year
            FROM   snorri.books
            WHERE  isbn = $varchar""".query(Book.skunkDecoder)

    // TODO: Using Session just to get it up and running.
    //  Will have to use something like connection pool or transaction manager
    Session
      .single[IO](
        host = "localhost",
        port = 5432,
        database = "snorri",
        user = "postgres",
        password = Some("password")
      )
      .use { s =>
        for {
          q <- s.prepare(query)
          b <- q.unique(isbn)
        } yield b
      }
      .attempt
      .map {
        case Right(book) =>
          println(book)
          Some(book)
        case Left(err) =>
          err.fillInStackTrace().printStackTrace()
          None
      }
      .unsafeRunSync()

  private def loadBooks(): Map[String, Book] =
    decode[List[Book]](retrieveBooks()) match {
      case Right(books) =>
        books.map(b => b.isbn -> b).toMap
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
