package actors

import akka.actor.Actor
import model.{Speed, MessageBigDecimals}
import utils.{SendIfChanged, BigDecimalRounding}

class SpeedActor extends Actor with BigDecimalRounding with SendIfChanged[Speed] {

  override def receive: Receive = {
    case MessageBigDecimals(bigDecimals) =>
      sendIfChanged(Speed(r(bigDecimals(0), 0), r(bigDecimals(3), 0)))
  }
}
