package actors

import akka.actor.Actor
import model.{Speed, MessageBigDecimals}
import play.api.Logger
import utils.BigDecimalRounding

class SpeedActor extends Actor with BigDecimalRounding {

  var lastReceived: Option[Speed] = None

  override def receive: Receive = {
    case MessageBigDecimals(bigDecimals) => {
      val speed = Speed(r(bigDecimals(0), 0), r(bigDecimals(3), 0))

      if(lastReceived.isDefined && lastReceived.get == speed){
        Logger.debug(s"dropping speed $speed because last one was the same")
      } else {
        ActorRegistry.websocketRegistry ! SendMessageToWebSockets(speed)
        lastReceived = Some(speed)
      }
    }
  }
}
