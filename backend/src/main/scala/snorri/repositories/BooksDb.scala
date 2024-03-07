package snorri.repositories

import io.circe.parser.decode
import snorri.models.Book
import snorri.utils.use
import cats.syntax.option.catsSyntaxOptionId
import cats.effect._
import skunk._
import skunk.implicits._
import skunk.codec.all._
import natchez.Trace.Implicits.noop
import skunk.Session

import scala.io.{BufferedSource, Source}
import snorri.models.Book.bookDecoder
import cats.effect.unsafe.IORuntime

import cats.effect.unsafe.implicits.global

object BooksDb:

  val session: Resource[IO, Session[IO]] =
    Session.single(
      host = "localhost",
      port = 5432,
      database = "snorri",
      user = "postgres",
      password = Some("password")
    )

  val all: Map[String, Book] = loadBooks()

  /* val bookSkunkDecoder: Decoder[Book] =
    (int4 ~ varchar ~ varchar ~ varchar ~ int4 ~ int4).map { case (id, isbn, name, author, pages, publishedYear) =>
      Book(id, isbn, name, author, pages, publishedYear)
    } */

  import IORuntime.global

  def find(isbn: String): Option[Book] =
    val dbCon: Resource[IO, Session[IO]] = Session.single(
      host = "localhost",
      port = 5432,
      database = "snorri",
      user = "postgres",
      password = Some("password")
    )

    val single: Query[Void, String] =
      sql"SELECT name FROM snorri.books fetch first 1 rows only".query(varchar)

    dbCon
      .use { session =>
        session.unique(sql"select current_date".query(date)).map { d =>
          Book(123, d.toString, d.toString, d.toString, 200, 2004).some
        }
        /* session.prepare(single).flatMap { ps =>
        ps.stream("U%", 32)
          .evalMap(c => IO.println(c))
          .compile
          .drain
      } */
      }
      .unsafeRunSync()

    /* val e: Query[String, Book] =
      sql"""
        SELECT id, isbn, name, author, pages, publishe_year
        FROM   snorri.books
        WHERE  isbn = $varchar""".query(bookDecoder)


        //session.prepare(q).use(_.execute(name)).void

    dbCon.use { s =>
      s.prepare(e).flatMap { ps =>
        ps.stream("U%", 64)
          .evalMap(c => IO.println(c))
          .compile
          .drain
      }
    } */

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
