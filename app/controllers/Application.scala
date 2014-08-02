package controllers

import actors._
import play.api.libs.json.JsValue
import play.api.mvc._
import play.api.Play.current

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def websocket = WebSocket.acceptWithActor[String, JsValue] { request => out =>
    WebSocketActor.props(out)
  }
}
