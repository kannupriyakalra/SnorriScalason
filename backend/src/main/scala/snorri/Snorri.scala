package snorri

import krop.all.*

object Snorri {
  val route =
    Route(
      Request.get(Path.root / "echo" / Param.string),
      Response.ok(Entity.text)
    ).handle(param => s"Your message was ${param}")
}
