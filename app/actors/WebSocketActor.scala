package actors

import actors.UDPConnectionStatusActorMessages.SendStatusToWebSocketActor
import akka.actor.{Props, ActorRef, Actor}
import model.{UDPConnectionStatus, GPSPosition, PitchRollHeading}
import play.api.libs.json.{Writes, Json}

/**
 * Created by benjamin on 8/1/14.
 */
object WebSocketActor {
  def props(out: ActorRef) = Props(new WebSocketActor(out))
}
class WebSocketActor(out: ActorRef) extends Actor {

  ActorRegistry.websocketRegistry ! RegisterWebSocket(self)
  ActorRegistry.udpConnectionStatusActor ! SendStatusToWebSocketActor(self)

  implicit val positionFormat = new Writes[GPSPosition] {
    def writes(position: GPSPosition) = Json.obj(
      "type" -> "position",
      "lat" -> position.lat,
      "lon" -> position.lon,
      "ftmsl" -> position.ftmsl,
      "ftagl" -> position.ftagl
    )
  }

  implicit val pitchRollHeadingFormat = new Writes[PitchRollHeading] {
    def writes(pitchRollHeading: PitchRollHeading) = Json.obj(
      "type" -> "pitchRollHeading",
      "pitch" -> pitchRollHeading.pitch,
      "roll" -> pitchRollHeading.roll,
      "trueHeading" -> pitchRollHeading.trueHeading,
      "magHeading" -> pitchRollHeading.magHeading
    )
  }

  implicit val udpConnectionStatusFormat = new Writes[UDPConnectionStatus] {
    def writes(udpConnectionStatus: UDPConnectionStatus) = Json.obj(
      "type" -> "udpConnectionStatus",
      "status" -> udpConnectionStatus.status
    )
  }

  def receive = {
    case position: GPSPosition => out ! Json.toJson(position)
    case pitchRollHeading: PitchRollHeading => out ! Json.toJson(pitchRollHeading)
    case status: UDPConnectionStatus => out ! Json.toJson(status)
    case msg => println(msg)
  }

  override def postStop() = {
    ActorRegistry.websocketRegistry ! DeregisterWebSocket(self)
  }
}

class WebSocketRegistry extends Actor {

  var webSockets = Set[ActorRef]()

  def receive = {
    case RegisterWebSocket(socket) => webSockets = webSockets + socket
    case DeregisterWebSocket(socket) => webSockets = webSockets - socket
    case SendMessageToWebSockets(message) => webSockets foreach {_ ! message}
  }
}
