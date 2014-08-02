package controllers

import java.util.concurrent.TimeUnit

import actors._
import model._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import akka.pattern.ask
import akka.util.Timeout

import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  def index = Action {
      Ok(views.html.index())
  }

  implicit val gpsPositionFormat = Json.format[GPSPosition]
  implicit val pitchRollHeadingFormat = Json.format[PitchRollHeading]
  implicit val pitchAndGps = Json.format[XPlaneData]


  def websocket = WebSocket.acceptWithActor[String, JsValue] { request => out =>
    WebSocketActor.props(out)
  }

}
