package actors

import akka.actor.Actor

case class PitchRollHeading(pitch: Float, roll: Float, trueHeading: Float, magHeading: Float)

object GetPitchRollHeading

class PitchRollHeadingActor extends Actor {

  var pitchRollHeading: Option[PitchRollHeading] = None

  override def receive: Receive = {
    case MessageFloats(floats) => pitchRollHeading = Some(PitchRollHeading(floats(0), floats(1), floats(2), floats(3)))
    case GetPitchRollHeading => sender ! pitchRollHeading
  }



}
