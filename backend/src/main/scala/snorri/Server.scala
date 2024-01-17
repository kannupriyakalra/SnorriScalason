package snorri

import krop.all.*
import krop.all.given

object Server {
  private val app: Application =
    Snorri.getBooksRoute
      .orElse(Snorri.echoRoute)
      .orElse(Application.notFound)

  @main def go(): Unit =
    ServerBuilder.default.withApplication(app).run()
}
