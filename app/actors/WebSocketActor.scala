package actors

import akka.actor.{Props, ActorRef, Actor}
import model.GPSCoords

/**
 * Created by benjamin on 8/1/14.
 */
object WebSocketActor {
  def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {
  ActorRegistry.websocketRegistry ! RegisterWebSocket(this)

  def receive = {
    case coors: GPSCoords => out ! coors.toString
    case msg => println(msg)
  }

  override def postStop() = {
    ActorRegistry.websocketRegistry ! DeregisterWebSocket(this)
  }
}

class WebSocketRegistry extends Actor {

  var webSockets = Set[WebSocketActor]()

  def receive = {
    case RegisterWebSocket(socket) => webSockets = webSockets + socket
    case DeregisterWebSocket(socket) => webSockets = webSockets - socket
    case SendMessageToWebSockets(message) => webSockets foreach {_.self ! message}
  }
}

case class RegisterWebSocket(socket: WebSocketActor)
case class DeregisterWebSocket(socket: WebSocketActor)
case class SendMessageToWebSockets(message: Any)