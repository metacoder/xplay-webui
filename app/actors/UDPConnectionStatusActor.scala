package actors

import akka.actor.{ActorRef, Actor}
import model.UDPConnectionStatus

object UDPConnectionStatusActorMessages {
  object Tick
  object MessageReceived
  object Bound
  object Error
  case class SendStatusToWebSocketActor(webSocketActor: ActorRef)

  val statusError = UDPConnectionStatus("error")
  val statusInitializing = UDPConnectionStatus("initializing")
  val statusReceiving = UDPConnectionStatus("receiving")
  val statusWaiting = UDPConnectionStatus("waiting")
}

class UDPConnectionStatusActor extends Actor {

  import UDPConnectionStatusActorMessages._
  import ActorRegistry.websocketRegistry

  var lastMessageReceived: Option[Long] = None

  override def receive: Receive = {

    case MessageReceived =>
      websocketRegistry ! SendMessageToWebSockets(statusReceiving)
      lastMessageReceived = Some(System.currentTimeMillis)
      context.become(receiving)

    case Bound =>
      websocketRegistry ! SendMessageToWebSockets(statusWaiting)
      context.become(waitingForXplaneData)

    case Error =>
      websocketRegistry ! SendMessageToWebSockets(statusError)
      context.become(error)

    case SendStatusToWebSocketActor(actor) =>
      actor ! statusInitializing
  }

  def receiving: Receive = {

    case Tick if lastMessageReceived.get < System.currentTimeMillis() - 1000 =>
      websocketRegistry ! SendMessageToWebSockets(statusWaiting)
      ActorRegistry.demoFileActor ! statusWaiting
      context.become(waitingForXplaneData)

    case MessageReceived =>
      lastMessageReceived = Some(System.currentTimeMillis())

    case SendStatusToWebSocketActor(actor) =>
      actor ! statusReceiving

  }

  def waitingForXplaneData: Receive = {

    case MessageReceived =>
      lastMessageReceived = Some(System.currentTimeMillis)
      websocketRegistry ! SendMessageToWebSockets(statusReceiving)
      context.become(receiving)

    case SendStatusToWebSocketActor(actor) =>
      actor ! statusWaiting

  }

  def error: Receive = {
    case SendStatusToWebSocketActor(actor) => actor ! statusError
  }
}
