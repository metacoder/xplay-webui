package actors

import actors.UDPConnectionStatusActorMessages.SendStatusToWebSocketActor
import akka.actor.{Props, ActorRef, Actor}
import model.{Speed, UDPConnectionStatus, GPSPosition, PitchRollHeading}
import play.api.Logger
import play.api.libs.json.{Writes, Json}
import utils.JSONMarshaller

/**
 * Created by benjamin on 8/1/14.
 */
object WebSocketActor {
  def props(out: ActorRef) = Props(new WebSocketActor(out))
}


class WebSocketActor(out: ActorRef) extends Actor with JSONMarshaller {

  ActorRegistry.websocketRegistry ! RegisterWebSocket(self)
  ActorRegistry.udpConnectionStatusActor ! SendStatusToWebSocketActor(self)

  def receive = {
    case position: GPSPosition => out ! Json.toJson(position)
    case pitchRollHeading: PitchRollHeading => out ! Json.toJson(pitchRollHeading)
    case speed: Speed => out ! Json.toJson(speed)
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
