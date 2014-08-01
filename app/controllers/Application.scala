package controllers

import java.util.concurrent.TimeUnit

import actors._
import play.api.mvc._
import akka.pattern.ask
import akka.util.Timeout

import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {


  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  def index = Action.async {
    val udpStatusFuture = ActorRegistry.xplaneDataReceiver ? GetUDPConnectionStatus
    val gpsCoordsFuture = ActorRegistry.gpsCoordsActor ? GetGPSCoords
    val pitchRollHeadingFuture = ActorRegistry.pitchRollHeadingActor ? GetPitchRollHeading

    val yielded = for {
      udpStatus <- udpStatusFuture
      gpsData <- gpsCoordsFuture
      pitchRollHeading <- pitchRollHeadingFuture
    } yield (udpStatus.toString, gpsData.asInstanceOf[Option[GPSCoords]], pitchRollHeading.asInstanceOf[Option[PitchRollHeading]])


    yielded.map {
      case (udpStatus, gpsData, pitchRollHeading) => Ok(views.html.index("Hello", udpStatus, gpsData, pitchRollHeading))
    }
  }

  def websocket = WebSocket.acceptWithActor[String, String] { request => out =>
    WebSocketActor.props(out)
  }
}
