package snorri

import krop.all.*
import krop.all.given

object Server {
  private val app: Application =
    Snorri.getBookByIdRoute
      .orElse(Snorri.addBookRoute)
      .orElse(Snorri.getAllBooksRoute) // FUTURE: Need help from Zainab using FS2 to stream books
      .orElse(Snorri.echoRoute)
      .orElse(Application.notFound)

  @main def go(): Unit =
    ServerBuilder.default.withApplication(app).run()
}
