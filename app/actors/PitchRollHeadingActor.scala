package actors

import akka.actor.Actor
import model.{MessageFloats, PitchRollHeading}

class PitchRollHeadingActor extends Actor {

  override def receive: Receive = {
    case MessageFloats(floats) => {
      val pitchRollHeading = PitchRollHeading(floats(0), floats(1), floats(2), floats(3))
      ActorRegistry.websocketRegistry ! SendMessageToWebSockets(pitchRollHeading)
    }
  }
}
