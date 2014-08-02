package actors

import akka.actor.Actor
import model.{MessageFloats, PitchRollHeading}

class PitchRollHeadingActor extends Actor {

  var lastPitchRollHeading: Option[PitchRollHeading] = None

  override def receive: Receive = {
    case MessageFloats(floats) => {
      val pitchRollHeading = PitchRollHeading(floats(0), floats(1), floats(2), floats(3))
      lastPitchRollHeading = Some(pitchRollHeading)
      ActorRegistry.websocketRegistry ! SendMessageToWebSockets(pitchRollHeading)
    }
    case GetPitchRollHeading => sender ! lastPitchRollHeading
  }
}
