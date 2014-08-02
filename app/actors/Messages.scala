package actors

import akka.actor.ActorRef

object GetGPSPosition
object GetPitchRollHeading
object GetUDPConnectionStatus

// WebSockets
case class RegisterWebSocket(socket: ActorRef)
case class DeregisterWebSocket(socket: ActorRef)
case class SendMessageToWebSockets(message: Any)