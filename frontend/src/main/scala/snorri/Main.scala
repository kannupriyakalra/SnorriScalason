package example

import cats.effect.IO
import tyrian.Html.*
import tyrian.*

import scala.scalajs.js.annotation.*
import cats.syntax.group

@JSExportTopLevel("Snorri")
object Main extends TyrianIOApp[Msg, Model]:

  def router: Location => Msg = Routing.none(Msg.NoOp)

  def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    (Model.init, Cmd.None)

  def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case Msg.Increment => (model + 1, Cmd.None)
    case Msg.Decrement => (model - 1, Cmd.None)
    case Msg.NoOp      => (model, Cmd.None)

  def view(model: Model): Html[Msg] =
    div(
      header(
        nav(`class` := "sticky mx-auto px-2 sm:px-6 lg:px-8 bg-slate-800 text-gray-300 border-b-2 border-slate-700")(
          div(`class` := "flex h-16 items-center justify-between")(
            // Left group
            div(
              h5(
                `class` := "font-extrabold hover:bg-slate-700 hover:text-white rounded-md border-2 border-slate-700 px-3 py-2"
              )(
                a(href := "/")("S")
              )
            ),
            // Right group
            div(`class` := "flex items-center justify-between")(
              button(`class` := "hover:bg-slate-700 hover:text-white rounded-md px-3 py-2 mr-3")("Login"),
              button(`class` := "hover:bg-slate-700 hover:text-white rounded-md px-3 py-2 mr-3")("Signup")
            )
          )
        )
      ),
      main(`class` := "px-2 sm:px-6 lg:px-8 mt-4")(
        h1(`class` := "text-3xl")(text("Snorri"), span(`class` := "font-extrabold")("Bookson")),
        button(onClick(Msg.Decrement))("-"),
        div(model.toString),
        button(onClick(Msg.Increment))("+")
      )
    )

  def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.None

opaque type Model = Int
object Model:
  def init: Model = 0

  extension (i: Model)
    def +(other: Int): Model = i + other
    def -(other: Int): Model = i - other

enum Msg:
  case Increment, Decrement, NoOp
