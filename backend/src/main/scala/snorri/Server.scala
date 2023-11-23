package snorri

import krop.all.{*, given}

object Server {
  val app: Application = Snorri.route.orElse(Application.notFound)

  @main def go(): Unit =
    ServerBuilder.default.withApplication(app).run()
}
