package snorri.repositories

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import io.circe.parser.decode
import natchez.Trace.Implicits.noop
import skunk.codec.all.{varchar, int4, text}
import skunk.implicits.sql
import skunk.{Query, Session}
import snorri.models.*
import snorri.utils.use

import scala.io.{BufferedSource, Source}
import cats.effect.kernel.Resource
import skunk.Command
import skunk.data.Completion.Insert

object BooksDb:
  val all: Map[String, Book] = loadBooks()

  def find(isbn: String): Option[Book] =
    val query: Query[String, Book] =
      sql"""SELECT id, isbn, name, author, pages, published_year
            FROM   snorri.books
            WHERE  isbn = $varchar""".query(Book.skunkDecoder)

    // TODO: Using Session just to get it up and running.
    //  Will have to use something like connection pool or transaction manager
    dbSession()
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

  private val enc = (text *: text *: text *: int4 *: int4).values.to[AddBookInput]

  private val insertBookCmd: Command[AddBookInput] =
    sql"""
    insert into snorri.books (isbn, name, author, pages, published_year) values $enc
    """.command

  def addBook(b: AddBookInput): Unit = {
    dbSession()
      .use { session =>
        session
          .prepare(insertBookCmd)
          .flatMap(_.execute(b))
          .attempt
          .map {
            case Left(ex) =>
              ex.fillInStackTrace().printStackTrace()
              throw ex
            case Right(Insert(1)) =>
              println(s"Inserted book: $b")
              ()
            case Right(other) =>
              println(s"addBook did not insert 1 record. $other")
              throw new Exception(s"AddBook: $other")
          }
      }
      .unsafeRunSync()
  }

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

  private def dbSession(): Resource[IO, Session[IO]] =
    Session
      .single[IO](
        host = "localhost",
        port = 5432,
        database = "snorri",
        user = "postgres",
        password = Some("password")
      )
