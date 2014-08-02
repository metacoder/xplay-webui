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

  def index = Action.async {
    val udpStatusFuture = ActorRegistry.xplaneDataReceiver ? GetUDPConnectionStatus

    udpStatusFuture.map {
      case udpStatus => Ok(views.html.index(udpStatus.asInstanceOf[UDPConnectionStatus].status))
    }
  }

  implicit val gpsPositionFormat = Json.format[GPSPosition]
  implicit val pitchRollHeadingFormat = Json.format[PitchRollHeading]
  implicit val pitchAndGps = Json.format[XPlaneData]

  def json = Action.async {
    allData().map {
      case (udpStatus, gpsPosition, pitchRollHeading) => {
        Ok(Json.toJson(XPlaneData(gpsPosition, pitchRollHeading, udpStatus)))
      }
    }
  }

  def websocket = WebSocket.acceptWithActor[String, JsValue] { request => out =>
    WebSocketActor.props(out)
  }

  private def allData() = {
    val udpStatusFuture = ActorRegistry.xplaneDataReceiver ? GetUDPConnectionStatus
    val gpsPositionFuture = ActorRegistry.gpsPositionActor ? GetGPSPosition
    val pitchRollHeadingFuture = ActorRegistry.pitchRollHeadingActor ? GetPitchRollHeading

    for {
      udpStatus <- udpStatusFuture
      gpsPosition <- gpsPositionFuture
      pitchRollHeading <- pitchRollHeadingFuture
    } yield (udpStatus.asInstanceOf[UDPConnectionStatus].status, gpsPosition.asInstanceOf[Option[GPSPosition]], pitchRollHeading.asInstanceOf[Option[PitchRollHeading]])
  }
}
