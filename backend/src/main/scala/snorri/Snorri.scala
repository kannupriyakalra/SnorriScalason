package snorri

import krop.all.*
import snorri.models.Book
import snorri.repositories.BooksDb
import snorri.models.AddBookInput

object Snorri {
  val echoRoute =
    Route(
      Request.get(Path / "echo" / Param.string),
      Response.ok(Entity.text)
    ).handle(param => s"Your message was $param")

  val getAllBooksRoute =
    Route(
      Request.get(Path / "books"),
      Response.ok(Entity.jsonOf[Iterable[Book]])
    ).handle(() => BooksDb.all.values)

  val getBookByIdRoute =
    Route(
      Request.get(Path / "books" :? Query("id", Param.string)),
      Response.ok(Entity.jsonOf[Book]).orNotFound
    ).handle(BooksDb.find(_))

  val addBookRoute =
    Route(
      Request.put(Path / "addBook").withEntity(Entity.jsonOf[AddBookInput]),
      // Request.put(Path / "addBook").withEntity(Entity.text),
      Response.status(Status.Created, Entity.unit)
    ).handle(b =>  {
      BooksDb.addBook(b)
    })
}
