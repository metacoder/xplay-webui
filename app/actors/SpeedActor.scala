package actors

import akka.actor.Actor
import model.{Speed, MessageBigDecimals}

class SpeedActor extends Actor {

  override def receive: Receive = {
    case MessageBigDecimals(bigDecimals) => {
      val speed = Speed(bigDecimals(0), bigDecimals(3))
      ActorRegistry.websocketRegistry ! SendMessageToWebSockets(speed)
    }
  }
}
