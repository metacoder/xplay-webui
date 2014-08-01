package actors

import akka.actor.{Props, ActorRef, Actor}
import model.{GPSCoords,PitchRollHeading}
import play.api.libs.json.{Writes, Json}

/**
 * Created by benjamin on 8/1/14.
 */
object WebSocketActor {

  def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {
  ActorRegistry.websocketRegistry ! RegisterWebSocket(self)

  implicit val locationFormat = new Writes[GPSCoords] {
    def writes(location: GPSCoords) = Json.obj(
      "type" -> "location",
      "latitude" -> location.latitude,
      "longitude" -> location.longitude
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

  def receive = {
    case coords: GPSCoords => out ! Json.toJson(coords)
    case pitchRollHeading: PitchRollHeading => out ! Json.toJson(pitchRollHeading)
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

case class RegisterWebSocket(socket: ActorRef)
case class DeregisterWebSocket(socket: ActorRef)
case class SendMessageToWebSockets(message: Any)