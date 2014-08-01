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

  private def allData() = {
    val udpStatusFuture = ActorRegistry.xplaneDataReceiver ? GetUDPConnectionStatus
    val gpsCoordsFuture = ActorRegistry.gpsCoordsActor ? GetGPSCoords
    val pitchRollHeadingFuture = ActorRegistry.pitchRollHeadingActor ? GetPitchRollHeading

    for {
      udpStatus <- udpStatusFuture
      gpsData <- gpsCoordsFuture
      pitchRollHeading <- pitchRollHeadingFuture
    } yield (udpStatus.toString, gpsData.asInstanceOf[Option[GPSCoords]], pitchRollHeading.asInstanceOf[Option[PitchRollHeading]])

  }

  implicit val gpsCoordsFormat = Json.format[GPSCoords]
  implicit val pitchRollHeadingFormat = Json.format[PitchRollHeading]
  implicit val pitchAndGps = Json.format[XPlaneData]

  def indexJson = Action.async {
   allData().map {
     case (udpStatus, gpsData, pitchRollHeading) => {
       Ok(Json.toJson(XPlaneData(gpsData, pitchRollHeading, udpStatus)))
     }
   }
  }

  def websocket = WebSocket.acceptWithActor[String, JsValue] { request => out =>
    WebSocketActor.props(out)
  }
}
