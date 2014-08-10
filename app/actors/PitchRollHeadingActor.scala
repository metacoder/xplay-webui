package actors

import akka.actor.Actor
import model.{MessageBigDecimals, PitchRollHeading}
import utils.BigDecimalRounding

class PitchRollHeadingActor extends Actor with BigDecimalRounding with SaveLastMessage[PitchRollHeading] {

  receiver {
    case MessageBigDecimals(bds) =>
      sendIfChanged(PitchRollHeading(r(bds(0), 2), r(bds(1)), r(bds(2)), r(bds(3))))
  }
}


