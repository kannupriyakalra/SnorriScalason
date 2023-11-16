package snorri

import krop.all.{*, given}

object Server {
  val app: Application = Snorri.route.otherwise(Application.notFound)

  @main def go(): Unit =
    ServerBuilder.default.withApplication(app).run()
}
