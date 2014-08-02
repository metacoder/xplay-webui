package actors

import akka.actor.ActorRef

// WebSockets
case class RegisterWebSocket(socket: ActorRef)
case class DeregisterWebSocket(socket: ActorRef)
case class SendMessageToWebSockets(message: Any)