package actors

import akka.actor.Actor
import model.{MessageBigDecimals, PitchRollHeading}
import play.api.Logger
import utils.BigDecimalRounding

class PitchRollHeadingActor extends Actor with BigDecimalRounding {

  var lastReceived: Option[PitchRollHeading] = None

  override def receive: Receive = {

    case MessageBigDecimals(bds) => {

      val pitchRollHeading = PitchRollHeading(r(bds(0),2), r(bds(1)), r(bds(2)), r(bds(3)))

      if(lastReceived.isDefined && lastReceived.get == pitchRollHeading){
        Logger.debug(s"dropping pitch roll heading $pitchRollHeading because last one was the same")
      } else {
        ActorRegistry.websocketRegistry ! SendMessageToWebSockets(pitchRollHeading)
      }
      lastReceived = Some(pitchRollHeading)
    }
  }
}


