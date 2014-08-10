package actors

import akka.actor.Actor
import model.{Speed, MessageBigDecimals}
import utils.BigDecimalRounding

class SpeedActor extends Actor with BigDecimalRounding with SaveLastMessage[Speed] {

  receiver {
    case MessageBigDecimals(bigDecimals) =>
      sendIfChanged(Speed(r(bigDecimals(0), 0), r(bigDecimals(3), 0)))
  }
}
