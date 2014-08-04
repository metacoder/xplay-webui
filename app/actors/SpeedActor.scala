package actors

import akka.actor.Actor
import model.{Speed, MessageFloats}

class SpeedActor extends Actor {

  override def receive: Receive = {
    case MessageFloats(floats) => {
      val speed = Speed(floats(0), floats(3))
      ActorRegistry.websocketRegistry ! SendMessageToWebSockets(speed)
    }
  }
}
