package actors

import akka.actor.{Props, ActorRef, Actor}
import model.GPSCoords
import play.api.libs.json.Json

/**
 * Created by benjamin on 8/1/14.
 */
object WebSocketActor {

  def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {
  ActorRegistry.websocketRegistry ! RegisterWebSocket(self)

  implicit val gpsFormat = Json.format[GPSCoords]

  def receive = {
    case coords: GPSCoords => {
      val json = Json.toJson(coords).toString()
      out ! json
    }
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