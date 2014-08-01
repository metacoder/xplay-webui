package actors

import akka.actor.Actor
import model.{MessageFloats, GetPitchRollHeading, PitchRollHeading}




class PitchRollHeadingActor extends Actor {

  var pitchRollHeading: Option[PitchRollHeading] = None

  override def receive: Receive = {
    case MessageFloats(floats) => pitchRollHeading = Some(PitchRollHeading(floats(0), floats(1), floats(2), floats(3)))
    case GetPitchRollHeading => sender ! pitchRollHeading
  }



}
