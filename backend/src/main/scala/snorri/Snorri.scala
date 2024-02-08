package snorri

import krop.all.*
import snorri.models.Book
import snorri.repositories.BooksDb

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

  val getBookByIdRoute = {
    Route(
      Request.get(Path / "books" :? Query("id", Param.string)),
      Response.ok(Entity.jsonOf[Book]).orNotFound
    ).handle(BooksDb.find(_))
  }
}
